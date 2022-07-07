package com.example.game.Game.gameDataDto;

import com.example.game.Game.Game;
import com.example.game.Game.card.Card;
import com.example.game.Game.gameDataDto.response.*;
import com.example.game.Game.gameDataDto.response.CardsDto;
import com.example.game.Game.gameDataDto.subDataDto.DiscardDto;
import com.example.game.Game.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonStringBuilder  {

    public String gameStarter(Game game) throws JsonProcessingException {
        GameStarterResponseDto responseDto = new GameStarterResponseDto(game);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String poisonDamageCheckResponseDtoJsonBuilder(Player player, boolean gameOver) throws JsonProcessingException {
        PoisonDamageCheckResponseDto responseDto = new PoisonDamageCheckResponseDto(player, gameOver);
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

    public String additionalDrawResponseDtoJsonBuilder(Player player, Card card, boolean drawSuccess) throws JsonProcessingException {
        AdditionalDrawResponseDto responseDto = new AdditionalDrawResponseDto(player, card, drawSuccess);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String preTurnCheckResponseDtoJsonBuilder(Player player) throws JsonProcessingException {
        PreTurnCheckResponseDto responseDto = new PreTurnCheckResponseDto(player);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String cardUseResponseDtoJsonBuilder(List<Player> players, boolean gameOver) throws JsonProcessingException {
        CardUseResponseDto responseDto = new CardUseResponseDto(players, gameOver);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String endTurnResponseDtoJsonBuilder(Player player, Player nextPlayer) throws JsonProcessingException {
        EndTurnResponseDto responseDto = new EndTurnResponseDto(player, nextPlayer);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String noMoreDrawResponseDtoJsonBuilder() throws JsonProcessingException {
        NoMoreDrawResponseDto responseDto = new NoMoreDrawResponseDto();
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String discard(DiscardDto discardDto) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(discardDto);
    }

    public String endGameResponseDtoJsonBuilder(Boolean winningTeam) throws JsonProcessingException {
        EndGameResponseDto responseDto = new EndGameResponseDto(winningTeam);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }
}
