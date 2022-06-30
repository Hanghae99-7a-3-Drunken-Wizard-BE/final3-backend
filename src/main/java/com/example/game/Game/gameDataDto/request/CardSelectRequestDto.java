package com.example.game.Game.gameDataDto.request;

import com.example.game.Game.card.Card;
import com.example.game.Game.gameDataDto.subDataDto.CardsDto;
import com.example.game.Game.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CardSelectRequestDto {
    private Long playerId;
    private List<CardsDto> selectedCards;

    public CardSelectRequestDto(Player player, List<Card> cards) {
        this.playerId = player.getPlayerId();
        List<CardsDto> cardList = new ArrayList<>();
        for(Card card : cards) {
            CardsDto cardsDto = new CardsDto(card);
            cardList.add(cardsDto);
        }
        this.selectedCards = cardList;
    }
}
