package com.example.game.websocket;

import com.example.game.service.UserService;
import com.example.game.websocket.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final RedisPublisher redisPublisher;
    private final SimpMessageSendingOperations sendingOperations;
    private final UserService userService;

    @MessageMapping("/chat/send")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.JOIN.equals(message.getType())) {
            message.setMessage(message.getMessage());
            message.setConnectedUsers(userService.getConnectedUsers());
            sendingOperations.convertAndSend("/sub/public", message);
            System.out.println(message.getMessage());
        }
        if (ChatMessage.MessageType.LEAVE.equals(message.getType())) {
            message.setMessage(message.getMessage());
            message.setConnectedUsers(userService.getConnectedUsers());
            sendingOperations.convertAndSend("/sub/public", message);
            System.out.println(message.getMessage());
        }
        message.setMessage(message.getMessage());
        message.setConnectedUsers(userService.getConnectedUsers());
        sendingOperations.convertAndSend("/sub/public", message);
        System.out.println(message.getMessage());
    }
}