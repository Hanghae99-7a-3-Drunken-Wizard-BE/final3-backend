package com.example.game.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRoomCreateResponseDto {
    private String roomId;
    private String roomName;

    public GameRoomCreateResponseDto(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }
}
