package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.h2Package.Card;
import com.example.game.Game.h2Package.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EndTurnResponseDto {
    private PlayerDto player;
    private Long nextPlayerId;

    public EndTurnResponseDto (Player player, List<Card> cards, Player nextPlayer) throws JsonProcessingException {
        this.player = new PlayerDto(player, cards);
        this.nextPlayerId = nextPlayer.getPlayerId();
    }
}
