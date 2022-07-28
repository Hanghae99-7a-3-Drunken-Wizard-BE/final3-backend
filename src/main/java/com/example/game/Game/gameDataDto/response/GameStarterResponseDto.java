package com.example.game.Game.gameDataDto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GameStarterResponseDto {

    private String roomName;
    private List<PlayerDto> players;

    public GameStarterResponseDto(List<PlayerDto> players, String roomName) {
        this.roomName = roomName;
        this.players = players;
    }
}
