package com.example.game.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/chat/send")
    public ResponseEntity message(ChatMessage message) {
        if (ChatMessage.MessageType.JOIN.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 채팅방에 참여하였습니다.");
            sendingOperations.convertAndSend("/sub/public", message);
            System.out.println(message.getSender() + "님이 채팅방에 참여하였습니다.");
            return ResponseEntity.ok(message.getSender() + "님이 채팅방에 참여하였습니다.");
        }
        if (ChatMessage.MessageType.LEAVE.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 퇴장하였습니다.");
            sendingOperations.convertAndSend("/sub/public", message);
            System.out.println(message.getSender() + "님이 퇴장하였습니다.");
            return ResponseEntity.ok(message.getSender() + "님이 퇴장하였습니다.");
        }
        sendingOperations.convertAndSend("/sub/public", message);
        System.out.println("chatMessage : " + message.getMessage());
        return ResponseEntity.ok(message);
    }
}