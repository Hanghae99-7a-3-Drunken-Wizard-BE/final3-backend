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
public class PoisonDamageCheckResponseDto {
    private PlayerDto player;
    private boolean gameOver;
    private List<CardDetailResponseDto> cardsDrawed;

    public PoisonDamageCheckResponseDto (boolean gameOver, List<Card> cards) throws JsonProcessingException {
        this.gameOver = gameOver;
        List<CardDetailResponseDto> cardDetailResponseDtos = new ArrayList<>();
        for (Card card : cards) {
            cardDetailResponseDtos.add(new CardDetailResponseDto(card));
        }
        this.cardsDrawed = cardDetailResponseDtos;
    }

}
