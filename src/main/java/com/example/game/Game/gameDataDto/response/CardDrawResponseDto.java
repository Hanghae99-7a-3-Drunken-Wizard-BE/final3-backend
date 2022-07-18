package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.card.Card;
import com.example.game.Game.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CardDrawResponseDto {
    private int selectable;

    public CardDrawResponseDto(int selectable) throws JsonProcessingException {
        this.selectable = selectable;
    }
}
