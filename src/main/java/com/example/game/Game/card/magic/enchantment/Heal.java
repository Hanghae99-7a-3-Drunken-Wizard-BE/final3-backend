package com.example.game.Game.card.magic.enchantment;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ATTACK;
import static com.example.game.Game.card.CardType.ENCHANTMENT;

public class Heal extends Card {
    public Heal(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
        this.cardName = "Heal";
        this.description = "무시무시한 결투장 위에서도 가끔은 박애주의를 기대할 수 있을지도요. 대상을 치료합니다.";
        this.cardType = ENCHANTMENT;
        this.target = Target.SELECT;
        this.healthModifier = 3;
        this.manaCost = -4;
    }
}