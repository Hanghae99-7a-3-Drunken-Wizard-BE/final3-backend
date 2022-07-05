package com.example.game.Game.gameDataDto;

import com.example.game.Game.gameDataDto.request.*;
import com.example.game.Game.gameDataDto.subDataDto.DiscardDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ObjectBuilder {

    public PlayerRequestDto checkPlayer(String PlayerToCheck) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(PlayerToCheck, PlayerRequestDto.class);
    }

    public CardSelectRequestDto drawnCards(String drawnCardList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(drawnCardList, CardSelectRequestDto.class);
    }

    public UseCardDto cardUse(String cardUse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(cardUse, UseCardDto.class);
    }

    public EndGameRequestDto endGame(String endGameMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(endGameMessage, EndGameRequestDto.class);
    }

    public DiscardDto discard(String discard) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(discard, DiscardDto.class);
    }

    public GameStartRequestDto gameStart(String gameStart) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(gameStart, GameStartRequestDto.class);
    }
}
