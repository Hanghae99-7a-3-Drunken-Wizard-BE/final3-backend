package com.example.game.dto.response;

import com.example.game.model.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRoomUserResponseDto {
    private Long id;
    private String nickname;
    private boolean ready;
    private int winCount;
    private  int loseCount;

    public GameRoomUserResponseDto (Long id, String nickname, boolean ready, int winCount, int loseCount) {
        this.id = id;
        this.nickname = nickname;
        this.ready = ready;
        this.winCount = winCount;
        this.loseCount = loseCount;
    }
}
