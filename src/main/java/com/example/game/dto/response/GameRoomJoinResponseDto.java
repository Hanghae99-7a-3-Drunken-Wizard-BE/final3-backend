package com.example.game.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRoomJoinResponseDto {
    private boolean joinSuccess;
    private String roomId;
    private String roomName;

    public GameRoomJoinResponseDto(boolean joinSuccess, String roomId, String roomName) {
        this.joinSuccess = joinSuccess;
        this.roomId = roomId;
        this.roomName = roomName;
    }
}
