package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.card.Card;
import com.example.game.Game.card.CardType;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AdditionalDrawResponseDto {

    private boolean endDraw = true;
    private boolean isSuccess;
    private List<CardDetailResponseDto> cardsOnHand;


    public AdditionalDrawResponseDto(List<Card> cards, boolean drawSuccess) {
        this.isSuccess = drawSuccess;
        List<CardDetailResponseDto> responseDtos = new ArrayList<>();
        for(Card card : cards) {
            responseDtos.add(new CardDetailResponseDto(card));
        }
        this.cardsOnHand = responseDtos;
    }
}
