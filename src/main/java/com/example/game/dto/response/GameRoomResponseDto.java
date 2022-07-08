package com.example.game.dto.response;

import com.example.game.model.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameRoomResponseDto {
    private User user1;
    private User user2;
    private User user3;
    private User user4;

}
