package com.example.game.websocket;


import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.example.game.security.jwt.JwtDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class SessionConnectEventListener {

    private final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;
    private final GameRoomRepository gameRoomRepository;

    private static List<String> userList = new ArrayList<>();
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    // SessionConnect시 username을 전달하여 사용자 목록에 추가
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
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        User user = userRepository.findBySessionId(headerAccessor.getSessionId());
        if (user != null) {
            user.setRoomId(null);
            userRepository.save(user);
        }
        System.out.println("웹소켓 연결해제가 감지됨");
    }
}
