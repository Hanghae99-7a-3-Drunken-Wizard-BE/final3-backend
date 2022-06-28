package com.example.game.websocket;

import com.example.game.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatMessage {

    private String roomId;
    private String sender;
    private String message;

    public ChatMessage(UserDetailsImpl userDetails) {
        this.roomId = roomId;
        this.sender = userDetails.getUser().getNickname();
        this.message = message;
    }
}
