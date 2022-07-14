package com.example.game.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRoomJoinResponseDto {
    private boolean isSuccess;

    public GameRoomJoinResponseDto(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
