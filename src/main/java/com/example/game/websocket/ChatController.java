package com.example.game.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations sendingOperations;

    private static List<String> userList = new ArrayList<>();

    @MessageMapping("/chat/send")
    public ResponseEntity message(ChatMessage message) {
        if (ChatMessage.MessageType.JOIN.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 채팅방에 참여하였습니다.");
            return ResponseEntity.ok().body(message.getSender() + "님이 채팅방에 참여하였습니다, 구독 연결됨");
        }
        sendingOperations.convertAndSend("/sub/public", message);
        System.out.println("chatMessage : " + message.getMessage());
        return ResponseEntity.ok("chatMessage : " + message.getMessage());
    }

//    @SubscribeMapping("/sub/public")
//    public ResponseEntity subscribe(SessionSubscribeEventListener event) {
//        System.out.println("새로운 WebSocket이 연결되었습니다.");
//        String nickname = event.getNickname();
//        if (nickname != null) {
//            userList.add(nickname);
//            System.out.println(userList);
//        }
//        userList.stream()
//                .filter(distinctByKey(p -> p.equals(nickname))) // 중복되는 닉네임 제거.
//                .collect(Collectors.toList());
//        return ResponseEntity.ok().body(userList + "유저 리스트에서 " + nickname + " 유저를 추가했습니다.");
//    }
}