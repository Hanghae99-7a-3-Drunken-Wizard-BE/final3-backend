package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.card.Card;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PreTurnStartCheckResponseDto {
    private PlayerDto player;
    private boolean gameOver;
    private List<CardDetailResponseDto> cardsDrawed;

    public PreTurnStartCheckResponseDto(boolean gameOver, List<Card> cards) throws JsonProcessingException {
        this.gameOver = gameOver;
        List<CardDetailResponseDto> cardDetailResponseDtos = new ArrayList<>();
        for (Card card : cards) {
            cardDetailResponseDtos.add(new CardDetailResponseDto(card));
        }
        this.cardsDrawed = cardDetailResponseDtos;
    }

}
