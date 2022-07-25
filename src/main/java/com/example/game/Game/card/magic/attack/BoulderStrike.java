package com.example.game.Game.card.magic.attack;

import com.example.game.Game.h2Package.Game;
import com.example.game.Game.h2Package.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ATTACK;

public class BoulderStrike extends Card {
    public BoulderStrike(Game game) {
        this.game = game;
        this.cardName = "Boulder Strike";
        this.description = "짱돌은 아주 원초적인 무기이면서도 확실한 수단입니다. 대상에게 중간 피해를 입히고 기절시킵니다";
        this.cardType = ATTACK;
        this.target = Target.SELECT;
        this.healthModifier = -3;
        this.manaCost = -4;
        this.stunDuration = 1;
        this.sleepDuration = 0;
    }
}
