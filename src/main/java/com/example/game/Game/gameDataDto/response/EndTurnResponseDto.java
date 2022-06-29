package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.gameDataDto.PlayerDto;
import com.example.game.Game.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndTurnResponseDto {
    private PlayerDto player;
    private Long nextPlayerId;

    public EndTurnResponseDto (Player player, Player nextPlayer) throws JsonProcessingException {
        this.player = new PlayerDto(player);
        this.nextPlayerId = nextPlayer.getPlayerId();
    }
}
