package com.example.game.Game.card.magic.attack;

import com.example.game.Game.h2Package.Game;
import com.example.game.Game.h2Package.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ATTACK;

public class MagicMissile extends Card {

    public MagicMissile (Game game) {
        this.game = game;
        this.cardName = "Magic Missile";
        this.description = "오랜시간 검증된 마법사들의 친구, 마법의 화살입니다. 단일대상에게 보통의 피해를 줍니다";
        this.cardType = ATTACK;
        this.target = Target.SELECT;
        this.healthModifier = -3;
        this.manaCost = -2;
        this.sleepDuration = 0;
    }
}
