package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoisonDamageCheckResponseDto {
    private PlayerDto player;
    private boolean gameOver;

    public PoisonDamageCheckResponseDto (Player player, boolean gameOver) throws JsonProcessingException {
        this.player = new PlayerDto(player);
        this.gameOver = gameOver;
    }

}
