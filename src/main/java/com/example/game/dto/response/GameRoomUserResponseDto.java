package com.example.game.dto.response;

import com.example.game.model.user.User;

public class GameRoomUserResponseDto {
    private Long id;
    private String nickname;

    public GameRoomUserResponseDto (User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
    }
}
