//package com.example.game.websocket;
//
//import com.example.game.security.jwt.JwtDecoder;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cglib.core.internal.Function;
//import org.springframework.context.event.EventListener;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectedEvent;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Component
//public class StompHandler implements ChannelInterceptor {
//
//    private final JwtDecoder jwtDecoder;
//
//    @Autowired
//    private SimpMessageSendingOperations messagingTemplate;
//
//    private static List<String> userList = new ArrayList<>();
//
//    //중복 검색 방지용 출처: https://howtodoinjava.com/java8/java-stream-distinct-examples/
//    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
//        Map<Object, Boolean> map = new ConcurrentHashMap<>();
//        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
//    }
//
//    // SessionConnect시 nickname을 전달하여 사용자 목록에 추가
//    @EventListener
//    public ResponseEntity handleWebSocketConnectListener(SessionConnectedEvent event) {
//        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
//        String nickName = jwtDecoder.decodeNickname(headers.getNativeHeader("Authorization").get(0));
//        System.out.println(nickName + " 님이 WebSocket에 연결되었습니다.");
//
//        if (nickName != null) {
//            userList.add(nickName);
//            System.out.println(userList);
//        }
//        userList.stream()
//                .filter(distinctByKey(p -> p.equals(nickName)))
//                .collect(Collectors.toList());
//
//        System.out.println(userList);
//
//        return ResponseEntity.ok().body(userList + "유저 리스트에서 " + nickName + " 유저를 추가했습니다.");
//    }
//
//    // SessionDisconnect시 nickname을 전달하여 사용자 목록에서 삭제
//    @EventListener
//    public ResponseEntity handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
//
//        String nickName = jwtDecoder.decodeNickname(headers.getNativeHeader("Authorization").get(0));
//        System.out.println(nickName + " 님이 WebSocket에서 연결을 끊었습니다.");
//
//        if (nickName != null) {
//            ResponseEntity.ok("User Disconnected : " + nickName);
//
//            ChatMessage chatMessage = new ChatMessage();
//            chatMessage.setType(ChatMessage.MessageType.LEAVE);
//            chatMessage.setSender(nickName);
//
//            System.out.println("User Disconnected : " + nickName);
//
//            userList.remove(nickName);
//            System.out.println(userList);
//
//            messagingTemplate.convertAndSend("/sub/public", chatMessage);
//        }
//        userList.stream()
//                .filter(distinctByKey(p -> p.equals(nickName))) // 중복되는 닉네임 제거.
//                .collect(Collectors.toList());
//        return ResponseEntity.ok().body(userList + "유저 리스트에서 " + nickName + " 유저를 삭제하였습니다.");
//    }
//}
