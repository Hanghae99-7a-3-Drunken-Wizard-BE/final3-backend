package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.card.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NoMoreDrawResponseDto {
    private boolean endDraw = true;
    private List<CardDetailResponseDto> cardsOnHand;

    public NoMoreDrawResponseDto(List<Card> cards) {
        List<CardDetailResponseDto> cardDetailResponseDtos = new ArrayList<>();
        for(Card card : cards) {
            cardDetailResponseDtos.add(new CardDetailResponseDto(card));
        }
        this.cardsOnHand = cardDetailResponseDtos;
    }
}
