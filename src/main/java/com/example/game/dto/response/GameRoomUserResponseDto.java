package com.example.game.dto.response;

import com.example.game.model.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRoomUserResponseDto {
    private Long id;
    private String nickname;

    public GameRoomUserResponseDto (User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
    }
}
