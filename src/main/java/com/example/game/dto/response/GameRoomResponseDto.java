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

    public GameRoomResponseDto(String roomId, String roomName, List<User> rawUserList) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.userList =new ArrayList<>();
        List<GameRoomUserResponseDto> users = new ArrayList<>();
        for(User user : rawUserList) {
            users.add(new GameRoomUserResponseDto(user));
        }
        this.userList = users;
    }
}
