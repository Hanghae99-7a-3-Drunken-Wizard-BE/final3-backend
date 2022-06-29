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
    private Long playerId;
    private Long cardId;
    private boolean drawSuccess;

    public AdditionalDrawResponseDto(Player player, Card card) {
        this.playerId = player.getPlayerId();
        this.cardId = card.getCardId();
        if (player.getCharactorClass().equals(CharactorClass.INVOKER)
                && card.getCardType().equals(CardType.ATTACK)){
            this.drawSuccess = true;}
        else if (player.getCharactorClass().equals(CharactorClass.ENCHANTER)
                && card.getCardType().equals(CardType.ENCHANTMENT)){
            this.drawSuccess = true;}
        else if (player.getCharactorClass().equals(CharactorClass.WAROCK)
                && card.getCardType().equals(CardType.CURSE)){
            this.drawSuccess = true;}
        else {this.drawSuccess = false;}
    }
}
