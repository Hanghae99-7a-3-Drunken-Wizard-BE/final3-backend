package com.example.game.websocket;

import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.example.game.security.jwt.JwtDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class SessionSubscribeEventListener {

    private final JwtDecoder jwtDecoder;
    private final GameRoomRepository gameRoomRepository;
    private final UserRepository userRepository;
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
    public void handleSubscribeEvent(SessionSubscribeEvent event) throws JsonProcessingException {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String targetDestination = headers.getDestination();
        System.out.println(targetDestination + " 구독이벤트 구독주소 추적");
        if (targetDestination.equals("/game/**")) {
            String roomId = targetDestination.substring(6, 42);
            System.out.println(roomId + " 구독이벤트 내 룸아이디 조회");
            GameRoom room = gameRoomRepository.findByRoomId(roomId);
            List<User> userList = userRepository.findByRoomId(roomId);
            String userListMessage = jsonStringBuilder.gameRoomResponseDtoJsonBuilder(
                    roomId, room.getRoomName(), userList);
            GameMessage message = new GameMessage();
            message.setRoomId(roomId);
            message.setContent(userListMessage);
            message.setType(GameMessage.MessageType.JOIN);
            messagingTemplate.convertAndSend("/sub/game/" + roomId, message);
        }
    }

    // SessionSubscribeEvent로 게임 상태 변경 이벤트 처리
    @EventListener
    public void handleSubscribeToChatEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String targetDestination = headers.getDestination();
        System.out.println(targetDestination + " 구독이벤트 구독주소 추적");
        if (targetDestination.equals("/chat/**")) {
            User user = userRepository.findBySessionId(headers.getSessionId());
            ChatMessage chatMessage = new ChatMessage();
            if (user != null) {
                user.setPlaying(false);
                userRepository.save(user);
                chatMessage.setType(ChatMessage.MessageType.JOIN);
            }
            System.out.println("로비에서 구독 취소");
        }
    }

    @EventListener
    public void handleSubscribeToGameEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String targetDestination = headers.getDestination();
        System.out.println(targetDestination + " 구독이벤트 구독주소 추적");
        if (targetDestination.equals("/game/**")) {
            User user = userRepository.findBySessionId(headers.getSessionId());
            if (user != null) {
                user.setPlaying(true);
                userRepository.save(user);
            }
            System.out.println("로비에서 구독 취소 후 게임룸 구독");
        }
    }

        // SessionUnsubscribe로 유저 목록에서 삭제
//    @EventListener
//    public ResponseEntity handleUnsubscribeEvent(SessionUnsubscribeEvent event) {
//        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
//        String nickName = jwtDecoder.decodeUsername(headers.getFirstNativeHeader("Authorization"));
//
//        if (nickName != null) {
//            subUserList.remove(nickName);
//            System.out.println(subUserList);
//        }
//
//        System.out.println(subUserList);
//
//        return ResponseEntity.ok().body(subUserList + "구독 리스트에서 " + nickName + " 유저를 삭제했습니다." + subUserList.size() + " 명 접속 중");
//    }
}
