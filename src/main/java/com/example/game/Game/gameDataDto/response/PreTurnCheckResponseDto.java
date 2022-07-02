package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.player.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreTurnCheckResponseDto {
    private Long playerId;
    private boolean action;

    public PreTurnCheckResponseDto(Player player){
        this.playerId = player.getPlayerId();
        this.action = player.getStunnedDuration() <= 0 &&
                player.getSleepDuration() <= 0 &&
                player.getPetrifiedDuration() <= 0;
    }
}
