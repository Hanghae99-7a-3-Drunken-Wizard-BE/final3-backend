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
    private Long sender;
    private String nickname;
    private MessageType type;
    private Integer imageNum;
    private List<UserResponseDto> connectedUsers;

    public enum MessageType {
        CHAT,
        LEAVE,
        JOIN
    }

    public ChatMessage(String message, MessageType type, UserDetailsImpl userDetails, List<UserResponseDto> connectedUsers) {
        this.message = message;
        this.type = type;
        this.sender = userDetails.getUser().getId();
        this.nickname = userDetails.getUser().getNickname();
        this.imageNum = userDetails.getUser().getImageNum();
        this.connectedUsers = connectedUsers;
    }
}
