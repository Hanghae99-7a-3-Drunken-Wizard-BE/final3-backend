package com.example.game.Game.gameDataDto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UseCardDto {
    private Long playerId;
    private Long TargetPlayerID;
    private Long cardId;
}
