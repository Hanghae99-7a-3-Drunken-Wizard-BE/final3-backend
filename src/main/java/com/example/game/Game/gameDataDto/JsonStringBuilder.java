package com.example.game.Game.gameDataDto;

import com.example.game.Game.Game;
import com.example.game.Game.card.Card;
import com.example.game.Game.gameDataDto.response.*;
import com.example.game.Game.gameDataDto.subDataDto.DiscardDto;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.PlayerRepository;
import com.example.game.dto.response.GameRoomListResponseDto;
import com.example.game.dto.response.GameRoomResponseDto;
import com.example.game.model.user.User;
import com.example.game.websocket.GameRoom;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public String poisonDamageCheckResponseDtoJsonBuilder(Player player, boolean gameOver) throws JsonProcessingException {
        PoisonDamageCheckResponseDto responseDto = dtoGenerator.poisonDamageCheckResponseDtoMaker(player, gameOver);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String cardDrawResponseDtoJsonBuilder(Player player, List<Card> cardList) throws  JsonProcessingException {
        CardDrawResponseDto responseDto = dtoGenerator.cardDrawResponseDtoMaker(player, cardList);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String additionalDrawResponseDtoJsonBuilder(Card card, boolean drawSuccess) throws JsonProcessingException {
        AdditionalDrawResponseDto responseDto = new AdditionalDrawResponseDto(card, drawSuccess);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String preTurnCheckResponseDtoJsonBuilder(Player player) throws JsonProcessingException {
        PreTurnCheckResponseDto responseDto = new PreTurnCheckResponseDto(player);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String cardUseResponseDtoJsonBuilder(List<Player> players, boolean gameOver) throws JsonProcessingException {
        CardUseResponseDto responseDto = dtoGenerator.cardUseResponseDtoMaker(players, gameOver);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }

    public String endTurnResponseDtoJsonBuilder(Player player, Player nextPlayer) throws JsonProcessingException {
        EndTurnResponseDto responseDto = dtoGenerator.EndTurnResponseDtoMaker(player, nextPlayer);
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

    public String gameRoomResponseDtoJsonBuilder(String roomId, String roomName, List<User> userList) throws JsonProcessingException {
        GameRoomResponseDto responseDto = new GameRoomResponseDto(roomId, roomName, userList);
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(responseDto);
    }
}
