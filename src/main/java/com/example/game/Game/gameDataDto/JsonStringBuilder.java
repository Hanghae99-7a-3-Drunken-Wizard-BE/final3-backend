package com.example.game.Game.gameDataDto;

import com.example.game.Game.h2Package.Game;
import com.example.game.Game.h2Package.Card;
import com.example.game.Game.gameDataDto.response.*;
import com.example.game.Game.h2Package.GameRoom;
import com.example.game.Game.h2Package.Player;
import com.example.game.Game.repository.PlayerRepository;
import com.example.game.dto.response.GameRoomResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JsonStringBuilder  {

    private final PlayerRepository playerRepository;
    private final DtoGenerator dtoGenerator;

    public String gameStarter(Game game) throws JsonProcessingException {
        GameStarterResponseDto responseDto = dtoGenerator.gameStarterResponseDtoMaker(game);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String preTurnStartCheckResponseDtoJsonBuilder(Player player, boolean gameOver) throws JsonProcessingException {
        PreTurnStartCheckResponseDto responseDto = dtoGenerator.preTurnStartCheckResponseDtoMaker(player, gameOver);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String cardDrawResponseDtoJsonBuilder(int selectable) throws  JsonProcessingException {
        CardDrawResponseDto responseDto = dtoGenerator.cardDrawResponseDtoMaker(selectable);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String additionalDrawResponseDtoJsonBuilder(List<Card> cards, boolean drawSuccess) throws JsonProcessingException {
        AdditionalDrawResponseDto responseDto = new AdditionalDrawResponseDto(cards, drawSuccess);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String preTurnCheckResponseDtoJsonBuilder(Player player) throws JsonProcessingException {
        PreTurnCheckResponseDto responseDto = new PreTurnCheckResponseDto(player);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String cardUseResponseDtoJsonBuilder(List<Player> players, Card card, boolean gameOver) throws JsonProcessingException {
        CardUseResponseDto responseDto = dtoGenerator.cardUseResponseDtoMaker(players, card, gameOver);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String endTurnResponseDtoJsonBuilder(Player player, Player nextPlayer) throws JsonProcessingException {
        EndTurnResponseDto responseDto = dtoGenerator.EndTurnResponseDtoMaker(player, nextPlayer);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String noMoreDrawResponseDtoJsonBuilder(List<Card> cards) throws JsonProcessingException {
        NoMoreDrawResponseDto responseDto = new NoMoreDrawResponseDto(cards);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String discard(Player player, List<Card> cards, Card discard) throws JsonProcessingException {
        DiscardResponseDto responseDto = new DiscardResponseDto(player, cards, discard);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String gameRoomResponseDtoJsonBuilder(GameRoom gameRoom) throws JsonProcessingException {
        GameRoomResponseDto responseDto = dtoGenerator.gameRoomResponseDtoMaker(gameRoom);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String cardUseFailDtoJsonBuilder(boolean isSuccess) throws JsonProcessingException {
        CardUseFailDto responseDto = new CardUseFailDto(isSuccess);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String endGameResponseDtoJsonBuilder(Boolean isEnd) throws JsonProcessingException {
        EndGameResponseDto responseDto = new EndGameResponseDto(isEnd);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }
}
