package com.example.game.game.card.magic.attack;

import com.example.game.game.card.Card;
import com.example.game.game.card.Target;

import static com.example.game.game.card.CardType.ATTACK;

public class MagicMissile extends Card {

    public MagicMissile () {
        this.cardName = "Magic Missile";
        this.description = "오랜시간 검증된 마법사들의 친구, 마법의 화살입니다. 단일대상에게 보통의 피해를 줍니다";
        this.cardType = ATTACK;
        this.target = Target.SELECT;
        this.healthModifier = -3;
        this.manaCost = -2;
    }
}
