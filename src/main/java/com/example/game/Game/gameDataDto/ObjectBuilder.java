package com.example.game.Game.gameDataDto;

import com.example.game.Game.gameDataDto.request.UseCardDto;
import com.example.game.Game.gameDataDto.request.DrawPlayerRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ObjectBuilder {

    public DrawPlayerRequestDto checkPlayer(String PlayerToCheck) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(PlayerToCheck, DrawPlayerRequestDto.class);
    }

    public List<CardsDto> drawnCards(String drawnCardList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return Arrays.asList(objectMapper.readValue(drawnCardList, CardsDto[].class));
    }

    public UseCardDto cardUse(String cardUse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(cardUse, UseCardDto.class);
    }
}
