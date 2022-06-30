package com.example.game.Game.gameDataDto.subDataDto;

import com.example.game.Game.card.Card;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardsDto {
    private Long cardId;

    public CardsDto(Card card) {
        this.cardId = card.getCardId();
    }
}
