package com.example.game.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRoomJoinResponseDto {
    private boolean joinSuccess;
    private String roomId;

    public GameRoomJoinResponseDto(boolean joinSuccess, String roomId) {
        this.joinSuccess = joinSuccess;
        this.roomId = roomId;
    }
}
