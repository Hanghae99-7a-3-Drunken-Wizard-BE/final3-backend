package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.Game;
import com.example.game.Game.card.Card;
import com.example.game.Game.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameStarterResponseDto {
    private List<PlayerDto> players;

    public GameStarterResponseDto(Game game, List<Player> playerList) throws JsonProcessingException {
        List<PlayerDto> responseDtos = new ArrayList<>();
        for(Player player : playerList) {
            responseDtos.add(new PlayerDto(player));
        }
        this.players = responseDtos;
    }
}
