package com.example.game.Game.gameDataDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardsResponseDto {
    private Long cardId;

    public CardsResponseDto (Long cardId) {
        this.cardId = cardId;
    }
}
