package com.example.game.Game.gameDataDto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CardSelectRequestDto {
    private List<CardRequestDto> selectedCards;
}
