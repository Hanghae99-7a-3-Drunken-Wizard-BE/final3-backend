package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.Game;
import com.example.game.Game.card.Card;
import com.example.game.Game.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GameStarterResponseDto {
    private List<PlayerDto> players;

    public GameStarterResponseDto(List<PlayerDto> players) {
        this.players = players;
    }
}
