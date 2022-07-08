package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.card.Card;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CardsDto {
    private Long cardId;

    public CardsDto(Card card) {
        this.cardId = card.getCardId();
    }
}
