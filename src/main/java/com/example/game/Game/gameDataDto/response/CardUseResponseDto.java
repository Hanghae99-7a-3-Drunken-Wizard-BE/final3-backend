package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CardUseResponseDto {
    private List<PlayerDto> players;
    private boolean gameOver;

    public CardUseResponseDto(List<Player> players, boolean gameOver) throws JsonProcessingException {
        List<PlayerDto> playerDtos = new ArrayList<>();
        for (Player player : players) {
            PlayerDto playerDto = new PlayerDto(player);
            playerDtos.add(playerDto);
        }
        this.players = playerDtos;
        this.gameOver = gameOver;
    }
}
