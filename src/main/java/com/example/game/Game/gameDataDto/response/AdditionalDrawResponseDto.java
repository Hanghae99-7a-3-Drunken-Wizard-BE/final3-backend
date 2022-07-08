package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.card.Card;
import com.example.game.Game.card.CardType;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.player.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdditionalDrawResponseDto {
    private CardDetailResponseDto card;
    private boolean drawSuccess;

    public AdditionalDrawResponseDto(Player player, Card card, boolean drawSuccess) {
        this.card = new CardDetailResponseDto(card);
        this.drawSuccess = drawSuccess;
    }
}
