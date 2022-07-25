package com.example.game.dto.response;

import com.example.game.security.UserDetailsImpl;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailResponseDto {
    int winCount;
    int loseCount;
    double winRate;

    public UserDetailResponseDto(int winCount, int loseCount) {
        this.winCount = winCount;
        this.loseCount = loseCount;
        this.winRate = Math.round((((winCount/(winCount+loseCount))*100)*100)/100.0);
    }
}
