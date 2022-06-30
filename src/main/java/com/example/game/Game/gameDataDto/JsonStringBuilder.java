package com.example.game.Game.gameDataDto;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.gameDataDto.response.*;
import com.example.game.Game.gameDataDto.subDataDto.CardsDto;
import com.example.game.Game.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonStringBuilder  {

    public String gameStarter(GameRoom gameRoom) throws JsonProcessingException {
        GameStarterResponseDto responseDto = new GameStarterResponseDto(gameRoom);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String playerResponseDtoJsonBuilder(Player player) throws JsonProcessingException {
        com.example.game.Game.gameDataDto.PlayerDto responseDto = new com.example.game.Game.gameDataDto.PlayerDto(player);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String cardResponseDtoJsonBuilder(List<Card> cards)throws JsonProcessingException {
        List<CardsDto> cardIds = new ArrayList<>();
        for (Card card : cards) {
            cardIds.add(new CardsDto(card));
        }
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(cardIds);
    }

    public String cardDrawResponseDtoJsonBuilder(Player player, List<Card> cardList) throws  JsonProcessingException {
        CardDrawResponseDto responseDto = new CardDrawResponseDto(player, cardList);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String additionalDrawResponseDtoJsonBuilder(Player player, Card card) throws JsonProcessingException {
        AdditionalDrawResponseDto responseDto = new AdditionalDrawResponseDto(player, card);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String preTurnCheckResponseDtoJsonBuilder(Player player) throws JsonProcessingException {
        PreTurnCheckResponseDto responseDto = new PreTurnCheckResponseDto(player);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String cardUseResponseJsonBuilder(List<Player> players) throws JsonProcessingException {
        List<com.example.game.Game.gameDataDto.PlayerDto> playerDtos = new ArrayList<>();
        for (Player player : players) {
            com.example.game.Game.gameDataDto.PlayerDto playerDto = new com.example.game.Game.gameDataDto.PlayerDto(player);
            playerDtos.add(playerDto);
        }
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(playerDtos);
    }

    public String endTurnResponseDtoJsonBuilder(Player player, Player nextPlayer) throws JsonProcessingException {
        EndTurnResponseDto responseDto = new EndTurnResponseDto(player, nextPlayer);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }
}
