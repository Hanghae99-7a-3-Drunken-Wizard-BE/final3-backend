package com.example.game.websocket;


import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.example.game.security.jwt.JwtDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class SessionConnectEventListener {

    private final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;
    private final GameRoomRepository gameRoomRepository;
    private final JsonStringBuilder jsonStringBuilder;

    private static List<String> userList = new ArrayList<>();
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

////     SessionConnect시 username을 전달하여 사용자 목록에 추가
//    @EventListener
//    public List<String> handleWebSocketConnectListener(SessionConnectedEvent event) {
//        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
//        String username = jwtDecoder.decodeUsername(headers.getFirstNativeHeader("token"));
//
//        if (StompCommand.CONNECTED == headers.getCommand()) {
//            System.out.println(username + " 님이 WebSocket에 연결되었습니다.");
//
//            if (username != null) {
//                userList.add(username);
//                System.out.println(userList + "접속유저 리스트에서 " + username + " 유저를 추가하였습니다." + userList.size() + " 명 접속 중");
//            }
//            return userList;
//        }
//        return userList;
//    }

    // SessionDisconnect시 username을 전달하여 사용자 목록에서 삭제
//    @EventListener
//    public List<String> handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
//        String username = jwtDecoder.decodeUsername(headers.getFirstNativeHeader("token"));
//
//        if (StompCommand.DISCONNECT == headers.getCommand()) {
//            System.out.println(username + " 님이 WebSocket에서 연결을 끊었습니다.");
//            if (username != null) {
//                ResponseEntity.ok("User Disconnected : " + username);
//
//                ChatMessage chatMessage = new ChatMessage();
//                chatMessage.setType(ChatMessage.MessageType.LEAVE);
//                chatMessage.setSender(username);
//
//                System.out.println("User Disconnected : " + username);
//
//                userList.remove(username);
//                System.out.println(userList + "접속유저 리스트에서 " + username + " 유저를 삭제하였습니다." + userList.size() + " 명 접속 중");
//
//                messagingTemplate.convertAndSend("/sub/public", chatMessage);
//            }
//        }
//        return userList;
//    }

    @Transactional
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) throws JsonProcessingException {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        User user = userRepository.findBySessionId(headerAccessor.getSessionId());
        String roomId = user.getRoomId();

            if (user != null) {
                if (roomId != null) {

                    Long userId = user.getId();
                    GameRoom gameRoom = gameRoomRepository.findByRoomId(user.getRoomId());

                    if (Objects.equals(gameRoom.getPlayer1(), userId)) {
                        gameRoom.setPlayer1(null);
                    }
                    if (Objects.equals(gameRoom.getPlayer2(), userId)) {
                        gameRoom.setPlayer2(null);
                    }
                    if (Objects.equals(gameRoom.getPlayer3(), userId)) {
                        gameRoom.setPlayer3(null);
                    }
                    if (Objects.equals(gameRoom.getPlayer4(), userId)) {
                        gameRoom.setPlayer4(null);
                    }

                    String userListMessage = jsonStringBuilder.gameRoomResponseDtoJsonBuilder(gameRoom);
                    GameMessage message = new GameMessage();
                    message.setRoomId(roomId);
                    message.setContent(userListMessage);
                    message.setType(GameMessage.MessageType.UPDATE);
                    messagingTemplate.convertAndSend("/sub/game/" + roomId, message);

                    if (gameRoom.getPlayer1() == null &&
                            gameRoom.getPlayer2() == null &&
                            gameRoom.getPlayer3() == null &&
                            gameRoom.getPlayer4() == null) {
                        gameRoomRepository.delete(gameRoom);
                    }
                    user.setRoomId(null);
            }
        }
        user.setSessionId(null);

        System.out.println("웹소켓 연결해제가 감지됨");
    }
}
