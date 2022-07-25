package com.example.game.dto.response;

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
        this.winRate = Math.round(setWinRate(winCount, loseCount)*100)/100.0;
    }

    public double setWinRate(int winCount, int loseCount) {
        if(winCount == 0 && loseCount == 0) {
            winRate = 0.0;
        } else if(winCount == 0 && loseCount != 0){
            winRate = 0.0;
        } else if(winCount != 0 && loseCount == 0){
            winRate = 100.0;
        } else {
            winRate = (double)winCount / (double)(winCount + loseCount) * 100;
        }
        return winRate;
    }
}
