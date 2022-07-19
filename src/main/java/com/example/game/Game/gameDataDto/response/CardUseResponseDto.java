package com.example.game.Game.gameDataDto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CardUseResponseDto {
    private List<PlayerDto> players;
    private boolean gameOver;
}
