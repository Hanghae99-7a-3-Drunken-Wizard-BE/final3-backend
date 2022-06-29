package com.example.game.Game.gameDataDto;

import com.example.game.Game.GameRoom;
import com.example.game.Game.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameStarterDto {
    private Long gameId;
    private String players;

    public GameStarterDto(GameRoom gameRoom) throws JsonProcessingException {
        this.gameId = gameRoom.getGameRoomId();
        List<PlayerResponseDto> responseDtos = new ArrayList<>();
        List<Player> playerList = gameRoom.getPlayerList();
        for(Player player : playerList) {
            responseDtos.add(new PlayerResponseDto(player));
        }
        ObjectWriter ow = new ObjectMapper().writer();
        this.players = ow.writeValueAsString(responseDtos);
    }
}
