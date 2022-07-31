package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.h2Package.Card;
import com.example.game.Game.card.Target;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDetailResponseDto {
    private Long cardId;
    private String cardName;
    private String description;
    private Target target;
    private Integer manaCost;

    public CardDetailResponseDto(Card card) {
        if (card != null) {
            this.cardId = card.getCardId();
            this.cardName = card.getCardName();
            this.description = card.getDescription();
            this.target = card.getTarget();
            this.manaCost = card.getManaCost();
        }
    }
}
