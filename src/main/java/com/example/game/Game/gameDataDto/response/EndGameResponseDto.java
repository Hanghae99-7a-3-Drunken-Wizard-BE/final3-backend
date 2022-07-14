package com.example.game.Game.gameDataDto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndGameResponseDto {
    private Boolean winningTeam;

    public EndGameResponseDto(Boolean winningTeam) {
        this.winningTeam = winningTeam;
    }
}
