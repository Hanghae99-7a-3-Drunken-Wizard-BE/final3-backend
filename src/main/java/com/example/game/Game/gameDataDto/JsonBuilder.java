package com.example.game.Game.gameDataDto;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonBuilder  {

    public String gameStarterDtoJsonBuilder(GameRoom gameRoom) throws JsonProcessingException {
        GameStarterDto gameStarterDto = new GameStarterDto(gameRoom);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(gameStarterDto);
    }

    public String playerResponseDtoJsonBuilder(Player player) throws JsonProcessingException {
        PlayerResponseDto responseDto = new PlayerResponseDto(player);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String cardResponseDtoJsonBuilder(List<Card> cards)throws JsonProcessingException {
        List<CardsResponseDto> cardIds = new ArrayList<>();
        for (Card card : cards) {
            cardIds.add(new CardsResponseDto(card.getCardId()));
        }
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(cardIds);
    }
}
