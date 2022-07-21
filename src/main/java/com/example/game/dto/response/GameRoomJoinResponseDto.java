package com.example.game.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRoomJoinResponseDto {
    private boolean joinSuccess;

    public GameRoomJoinResponseDto(boolean joinSuccess) {
        this.joinSuccess = joinSuccess;
    }
}
