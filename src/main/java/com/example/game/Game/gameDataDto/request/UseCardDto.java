package com.example.game.Game.gameDataDto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UseCardDto {
    private Long targetPlayerID;
    private Long cardId;
}
