package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.gameDataDto.PlayerDto;
import com.example.game.Game.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreTurnCheckResponseDto {
    private PlayerDto player;
    private boolean action;

    public PreTurnCheckResponseDto(Player player) throws JsonProcessingException {
        this.player = new PlayerDto(player);
        this.action = player.getStunnedDuration() <= 0 &&
                player.getSleepDuration() <= 0 &&
                player.getPetrifiedDuration() <= 0;
    }
}
