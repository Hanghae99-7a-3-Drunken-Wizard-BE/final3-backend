package com.example.game.dto.response;

import com.example.game.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameRoomResponseDto {
    private String roomId;
    private String roomName;
    private GameRoomUserResponseDto player1;
    private GameRoomUserResponseDto player2;
    private GameRoomUserResponseDto player3;
    private GameRoomUserResponseDto player4;

    public GameRoomResponseDto(String roomId, String roomName,
                               GameRoomUserResponseDto player1,
                               GameRoomUserResponseDto player2,
                               GameRoomUserResponseDto player3,
                               GameRoomUserResponseDto player4) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
    }
}
