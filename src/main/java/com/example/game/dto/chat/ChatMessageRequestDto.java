package com.example.game.dto.chat;

import com.example.game.model.chat.ChatMessage;

public class ChatMessageRequestDto {
    private ChatMessage.MessageType type;
    private Long roomId;
    private String sender;
    private String message;
    private long userCount;
//    private String profileImg;
}

