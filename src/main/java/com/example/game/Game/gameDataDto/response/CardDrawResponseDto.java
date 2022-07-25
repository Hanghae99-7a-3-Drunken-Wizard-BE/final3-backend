package com.example.game.Game.gameDataDto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDrawResponseDto {
    private int selectable;

    public CardDrawResponseDto(int selectable) throws JsonProcessingException {
        this.selectable = selectable;
    }
}
