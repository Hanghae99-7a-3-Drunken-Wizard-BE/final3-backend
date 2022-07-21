package com.example.game.Game.card.magic.attack;

import com.example.game.Game.h2Package.Game;
import com.example.game.Game.h2Package.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ATTACK;

public class PoisonArrow extends Card {
    public PoisonArrow(Game game) {
        this.game = game;
        this.cardName = "Poison Arrow";
        this.description = "적에게 선물은 독이 딱이고 마법화살에 실어보내면 더할나위 없습니다. 적에게 적은 피해를 입히고 중독시킵니다";
        this.cardType = ATTACK;
        this.target = Target.SELECT;
        this.healthModifier = -2;
        this.manaCost = -4;
        this.poisonDuration = 3;
        this.sleepDuration = 0;
    }
}
