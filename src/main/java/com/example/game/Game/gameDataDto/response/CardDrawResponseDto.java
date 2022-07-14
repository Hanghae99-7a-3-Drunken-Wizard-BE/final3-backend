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
public class CardDrawResponseDto {
    private int selectable;

    public CardDrawResponseDto(List<Card> cardsOnHand) throws JsonProcessingException {
        if (cardsOnHand.size() <= 6) {this.selectable = Math.min(6 - cardsOnHand.size(), 2);}
        else {throw new RuntimeException("카드소유한계 에러");}
    }
}
