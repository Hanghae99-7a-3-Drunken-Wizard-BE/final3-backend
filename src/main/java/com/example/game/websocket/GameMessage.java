package com.example.game.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameMessage {

    private String roomId;
    private Long sender;
    private String content;
    private MessageType type;

    public enum MessageType {
        JOIN, LEAVE, TALK, START, PRECHECK, DRAW, ENDDRAW, SELECT, TURNCHECK, USEFAIL, USECARD, USESPECIAL, DISCARD, ENDGAME, ENDTURN, UPDATE
    }
}
