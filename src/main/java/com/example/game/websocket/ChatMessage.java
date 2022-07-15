package com.example.game.websocket;

import com.example.game.dto.response.UserResponseDto;
import com.example.game.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    private String message;
//    private String roomId;
    private String sender;

    private MessageType type;

    private List<UserResponseDto> connectedUsers;

    public enum MessageType {
        CHAT, LEAVE, JOIN
    }

    public ChatMessage(String message, UserDetailsImpl userDetails) {
        this.message = message;
        this.sender = userDetails.getUser().getNickname();
    }
}
