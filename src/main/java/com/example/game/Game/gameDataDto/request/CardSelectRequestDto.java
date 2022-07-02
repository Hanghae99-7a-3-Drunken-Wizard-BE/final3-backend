package com.example.game.Game.gameDataDto.request;

import com.example.game.Game.card.Card;
import com.example.game.Game.gameDataDto.response.CardsDto;
import com.example.game.Game.player.Player;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CardSelectRequestDto {
    private Long playerId;
    private List<CardRequestDto> selectedCards;
}
