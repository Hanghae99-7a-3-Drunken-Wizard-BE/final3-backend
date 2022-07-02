package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.card.Card;
import com.example.game.Game.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;

public class CardDrawResponseDto {
    private Long playerId;
    private int selectable;
    private List<CardsDto> cardDrawed;

    public CardDrawResponseDto(Player player, List<Card> cards) throws JsonProcessingException {
        this.playerId = player.getPlayerId();
        if (player.getCardsOnHand().size() <= 6) {this.selectable = 6 - player.getCardsOnHand().size();}
        else {throw new RuntimeException("카드소유한계 에러");}
        List<CardsDto> cardIds = new ArrayList<>();
        for (Card card : cards) {
            cardIds.add(new CardsDto(card));
        }
        this.cardDrawed = cardIds;

    }
}
