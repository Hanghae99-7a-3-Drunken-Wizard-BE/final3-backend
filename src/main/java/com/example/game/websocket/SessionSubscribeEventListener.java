package com.example.game.websocket;

import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.h2Package.Game;
import com.example.game.Game.h2Package.GameRoom;
import com.example.game.Game.h2Package.Player;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.GameRoomRepository;
import com.example.game.Game.repository.PlayerRepository;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.example.game.security.jwt.JwtDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class SessionSubscribeEventListener {

    private final JwtDecoder jwtDecoder;
    private final GameRoomRepository gameRoomRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final JsonStringBuilder jsonStringBuilder;
    private final SimpMessageSendingOperations messagingTemplate;

    private static List<String> subUserList = new ArrayList<>();


//    // SessionSubscribe, Unsubscribe로 유저 목록 생성 or 삭제
//    @EventListener
//    public ResponseEntity SubscribeUserList(SessionSubscribeEvent subscribeEvent, SessionUnsubscribeEvent unsubscribeEvent) {
//        StompHeaderAccessor subscribedHeaderAccessor = StompHeaderAccessor.wrap(subscribeEvent.getMessage());
//        StompHeaderAccessor unsubscribedHeaderAccessor = StompHeaderAccessor.wrap(unsubscribeEvent.getMessage());
//
//        String subscribedNickname = (String) subscribedHeaderAccessor.getSessionAttributes().get("nickname");
//        String unsubscribedNickname = (String) unsubscribedHeaderAccessor.getSessionAttributes().get("nickname");
//
//        System.out.println("subscribedNickname : " + subscribedNickname);
//        System.out.println("unsubscribedNickname : " + unsubscribedNickname);
//
//        if (subscribedNickname != null) {
//            userList.add(subscribedNickname);
//            System.out.println(userList);
//            return ResponseEntity.ok().body(userList + "유저 리스트에서 " + subscribedNickname + " 유저를 추가했습니다.");
//        } else if (unsubscribedNickname != null) {
//            userList.remove(unsubscribedNickname);
//            System.out.println(userList);
//            return ResponseEntity.ok().body(userList + "유저 리스트에서 " + unsubscribedNickname + " 유저를 삭제하였습니다.");
//        }
//        userList.stream()
//                .filter(distinctByKey(p -> p.equals(subscribedNickname)))
//                .filter(distinctByKey(p -> p.equals(unsubscribedNickname)))
//                .collect(Collectors.toList());
//
//        System.out.println(userList);
//
//        return ResponseEntity.ok().body("유저 리스트 : " + userList);
//    }

    // SessionSubscribe로 유저 목록에 추가
    @EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String targetDestination = headers.getDestination();
        String sessionId = headers.getSessionId();
        System.out.println(targetDestination + " 구독이벤트 구독주소 추적");
        if (targetDestination.length() == 47) {
            String roomId = targetDestination.substring(11, 47);
            System.out.println(roomId + " 구독이벤트 내 룸아이디 조회");

            User user = userRepository.findBySessionId(sessionId);
            System.out.println(user.getUsername() + " 세션아이디로 유저 조회됨");
            user.setRoomId(roomId);
            userRepository.save(user);

            GameRoom room = gameRoomRepository.findByRoomId(roomId);

            if (room != null) {
                if (room.getPlayer1() == null) {
                    room.setPlayer1(user.getId() * -1);
                    System.out.println("플레이어 1 슬롯에 유저 배치");
                    gameRoomRepository.save(room);
                    return;
                }
                if (room.getPlayer2() == null) {
                    room.setPlayer2(user.getId() * -1);
                    System.out.println("플레이어 2 슬롯에 유저 배치");
                    gameRoomRepository.save(room);
                    return;
                }
                if (room.getPlayer3() == null) {
                    room.setPlayer3(user.getId() * -1);
                    System.out.println("플레이어 3 슬롯에 유저 배치");
                    gameRoomRepository.save(room);
                    return;
                }
                if (room.getPlayer4() == null) {
                    room.setPlayer4(user.getId() * -1);
                    System.out.println("플레이어 4 슬롯에 유저 배치");
                    gameRoomRepository.save(room);
                }
            }
        }
    }

    @EventListener
    public void handleSubscribeAtChatEvent(SessionSubscribeEvent event) throws JsonProcessingException {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String targetDestination = headers.getDestination();
        System.out.println(targetDestination + " 구독이벤트 구독주소 추적");
        if (targetDestination.equals("/sub/public")) {
            User user = userRepository.findBySessionId(headers.getSessionId());
            System.out.println("채팅에 구독중");
            if (user != null) {
                if (playerRepository.existsById(user.getId())) {
                    Player player = playerRepository.findById(user.getId()).orElseThrow(()->new NullPointerException("플레이어 없음"));
                    Game game = player.getGame();
                    player.setHealth(0);
                    player.setDead(true);
                    playerRepository.save(player);
                    List<Player> players = playerRepository.findByGame(game);
                    if (players.get(0).isDead() && players.get(1).isDead() && players.get(2).isDead() && players.get(3).isDead()) {
                        gameRepository.delete(game);
                    }
                }
                String roomId = user.getRoomId();
                if (roomId != null) {
                    Long userId = user.getId();
                    GameRoom gameRoom = gameRoomRepository.findByRoomId(user.getRoomId());
                    user.setRoomId(null);
                    userRepository.save(user);
                    if (gameRoom != null) {
                        if (gameRoom.getPlayer1() != null){
                            if (
                                    Objects.equals(gameRoom.getPlayer1(), userId) ||
                                            Objects.equals(gameRoom.getPlayer1() * -1, userId)
                            ) {
                                gameRoom.setPlayer1(null);
                                System.out.println("플레이어1 슬롯에서 제거됨");
                            }}
                        if (gameRoom.getPlayer2() != null){
                            if (
                                    Objects.equals(gameRoom.getPlayer2(), userId) ||
                                            Objects.equals(gameRoom.getPlayer2() * -1, userId)
                            ) {
                                gameRoom.setPlayer2(null);
                                System.out.println("플레이어2 슬롯에서 제거됨");
                            }}
                        if (gameRoom.getPlayer3() != null){
                            if (
                                    Objects.equals(gameRoom.getPlayer3(), userId) ||
                                            Objects.equals(gameRoom.getPlayer3() * -1, userId)
                            ) {
                                gameRoom.setPlayer3(null);
                                System.out.println("플레이어3 슬롯에서 제거됨");
                            }}
                        if (gameRoom.getPlayer4() != null){
                            if (
                                    Objects.equals(gameRoom.getPlayer4(), userId) ||
                                            Objects.equals(gameRoom.getPlayer4() * -1, userId)
                            ) {
                                gameRoom.setPlayer4(null);
                                System.out.println("플레이어4 슬롯에서 제거됨");
                            }}
                        gameRoomRepository.save(gameRoom);
                        String userListMessage = jsonStringBuilder.gameRoomResponseDtoJsonBuilder(gameRoom);
                        GameMessage message = new GameMessage();
                        message.setRoomId(roomId);
                        message.setContent(userListMessage);
                        message.setType(GameMessage.MessageType.UPDATE);
                        messagingTemplate.convertAndSend("/sub/wroom/" + roomId, message);
                    }
                }
            }
            System.out.println("로비에서 구독 취소");
        }
    }
}
