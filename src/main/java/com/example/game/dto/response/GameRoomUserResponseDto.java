package com.example.game.dto.response;

import com.example.game.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameRoomUserResponseDto {
    private Long id;
    private String nickname;
    private Boolean ready;
    private Integer winCount;
    private  Integer loseCount;

    public GameRoomUserResponseDto (Long id, String nickname, boolean ready, int winCount, int loseCount) {
        this.id = id;
        this.nickname = nickname;
        this.ready = ready;
        this.winCount = winCount;
        this.loseCount = loseCount;
    }
}
