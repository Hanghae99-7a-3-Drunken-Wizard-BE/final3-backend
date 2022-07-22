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
        if (ChatMessage.MessageType.JOIN.equals(message.getType()) && message.getConnectedUsers() != null) {
            message.setMessage(message.getMessage());
            message.setConnectedUsers(userService.getConnectedUsers());
            sendingOperations.convertAndSend("/sub/public", message);
            System.out.println(message.getSender() + " 님이 접속하였습니다.");
        }
        else if (ChatMessage.MessageType.LEAVE.equals(message.getType())) {
            message.setMessage(message.getMessage());
            message.setConnectedUsers(userService.getConnectedUsers());
            sendingOperations.convertAndSend("/sub/public", message);
            System.out.println(message.getSender() + " 님이 접속을 끊었습니다.");
        }
        else if (ChatMessage.MessageType.CHAT.equals(message.getType())){
            message.setMessage(message.getMessage());
            message.setConnectedUsers(null);
            sendingOperations.convertAndSend("/sub/public", message);
            System.out.println("Chat Message : " + message.getMessage());
        }
//        else if(GameMessage.MessageType.JOIN.equals(gameMessage.getType())){
//            message.setMessage(message.getSender() + " 님이 게임룸에 입장하였습니다.");
//            message.setConnectedUsers(userService.getConnectedUsers());
//            sendingOperations.convertAndSend("/sub/public", message);
//            System.out.println(message.getSender() + " 님이 게임룸에 입장하였습니다.");
//        }
    }
}