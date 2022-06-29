package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.GameRoom;
import com.example.game.Game.gameDataDto.PlayerDto;
import com.example.game.Game.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameStarterResponseDto {
    private Long gameId;
    private List<PlayerDto> players;

    public GameStarterResponseDto(GameRoom gameRoom) throws JsonProcessingException {
        this.gameId = gameRoom.getGameRoomId();
        List<PlayerDto> responseDtos = new ArrayList<>();
        List<Player> playerList = gameRoom.getPlayerList();
        for(Player player : playerList) {
            responseDtos.add(new PlayerDto(player));
        }
        this.players = responseDtos;
    }
}
