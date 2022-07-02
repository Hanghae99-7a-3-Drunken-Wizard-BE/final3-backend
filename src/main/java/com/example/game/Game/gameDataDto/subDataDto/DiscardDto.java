package com.example.game.Game.gameDataDto.subDataDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiscardDto {
    private Long playerId;
    private Long cardId;
}
