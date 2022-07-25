package com.example.game.Game.gameDataDto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardUseFailDto {
    private boolean isSuccess;

    public CardUseFailDto(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
