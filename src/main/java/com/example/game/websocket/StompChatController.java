package com.example.game.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class StompChatController {

//    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
//
//    private static Set<Long> userList = new HashSet<>();
//
//    //Client가 SEND할 수 있는 경로
//    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
//    //"/pub/chat/enter"
//    @MessageMapping(value = "/chat/enter")
//    public void enter(@Payload Long userId, ChatMessageDto message) {
//        userList.add(userId);
//        message.setMessage(message.getSender() + "님이 채팅방에 참여하였습니다.");
//        template.convertAndSend("/sub/chat", message);
//        ResponseEntity.ok().body(userList);
//    }
//
//    @MessageMapping(value = "/chat/message")
//    public void message(ChatMessageDto message) {
//        template.convertAndSend("/sub/chat", message);
//    }
}
