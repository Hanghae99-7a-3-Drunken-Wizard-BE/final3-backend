package com.example.game.Game.gameDataDto.response;

import com.example.game.model.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LobbyUserListDto {
    private List<UserDto> userList = new ArrayList<>();

    public LobbyUserListDto (List<User> userList) {
        for (User user : userList) {
            this.userList.add(new UserDto(user));
        }
    }
}
