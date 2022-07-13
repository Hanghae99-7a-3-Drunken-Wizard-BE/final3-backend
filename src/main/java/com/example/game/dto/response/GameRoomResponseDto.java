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
    private List<GameRoomUserResponseDto> userList;

    public GameRoomResponseDto(String roomId, String roomName, List<User> userList) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.userList =new ArrayList<>();
        for(User user : userList) {
            this.userList.add(new GameRoomUserResponseDto(user));
        }
    }
}
