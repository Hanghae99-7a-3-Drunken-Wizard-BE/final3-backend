package com.example.game.websocket;

import com.example.game.repository.user.UserRepository;
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
    private final UserRepository userRepository;

    @MessageMapping("/chat/send")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.JOIN.equals(message.getType())) {
            message.setMessage(message.getMessage());
            message.setConnectedUsers(userService.getConnectedUsers());
            sendingOperations.convertAndSend("/sub/public", message);
            System.out.println(message.getNickname() + " 님이 접속하였습니다.");
        }
//        else if (ChatMessage.MessageType.LEAVE.equals(message.getType())) {
//            System.out.println("채팅 LEAVE 메서드 작동중");
//            System.out.println(message.getNickname());
//            System.out.println(message.getSender());
//            message.setMessage(message.getNickname() + " 님이 접속을 끊었습니다.");
//            message.setConnectedUsers(userService.getConnectedUsers());
//            sendingOperations.convertAndSend("/sub/public", message);
//            System.out.println(message.getNickname() + " 님이 접속을 끊었습니다.");
//        }
        else if  (ChatMessage.MessageType.CHAT.equals(message.getType())){

            message.setMessage(message.getMessage());
            message.setConnectedUsers(null);
            message.setImageNum(userRepository.findById(message.getSender()).orElseThrow(
                    () -> new NullPointerException("유저없음")).getImageNum());
            sendingOperations.convertAndSend("/sub/public", message);
            System.out.println("Chat Message : " + message.getMessage());
        }
    }
}