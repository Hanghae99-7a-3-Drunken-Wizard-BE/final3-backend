package com.example.game.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations sendingOperations;

    private static List<String> userList = new ArrayList<>();


    @MessageMapping("/chat/send")
    public ResponseEntity message(ChatMessage message) {
        if (ChatMessage.MessageType.JOIN.equals(message.getType())) {
            userList.add(message.getSender());
            message.setMessage(message.getSender() + "님이 채팅방에 참여하였습니다.");
            sendingOperations.convertAndSend("/sub/public", message);
            System.out.println(userList);
            return ResponseEntity.ok().body(userList);
        }
        if (ChatMessage.MessageType.LEAVE.equals(message.getType())) {
            userList.remove(message.getSender());
            message.setMessage(message.getSender() + "님이 퇴장하였습니다.");
            sendingOperations.convertAndSend("/sub/public", message);
            System.out.println(userList);
            return ResponseEntity.ok().body(userList);
        }
        sendingOperations.convertAndSend("/sub/public", message);
        System.out.println("chatMessage : " + message.getMessage());
        System.out.println(userList);
        return ResponseEntity.ok().body(userList);
    }
}