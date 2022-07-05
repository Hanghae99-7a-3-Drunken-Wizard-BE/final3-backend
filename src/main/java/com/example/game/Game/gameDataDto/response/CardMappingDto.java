package com.example.game.Game.gameDataDto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CardMappingDto {
    private Long cardId;
    private String cardName;

    public CardMappingDto(Long cardId, String cardName) {
        this.cardId = cardId;
        this.cardName = cardName;
    }
}
