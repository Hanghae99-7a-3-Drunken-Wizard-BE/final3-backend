package com.example.game.game.card.magic.attack;

import com.example.game.game.card.Card;
import com.example.game.game.card.Target;

import static com.example.game.game.card.CardType.ATTACK;

public class BoulderStrike extends Card {

    public BoulderStrike() {
        this.cardName = "Boulder Strike";
        this.description = "짱돌은 아주 원초적인 무기이면서도 확실한 수단입니다. 대상에게 중간 피해를 입히고 기절시킵니다";
        this.cardType = ATTACK;
        this.target = Target.SELECT;
        this.healthModifier = -3;
        this.manaCost = -4;
        this.stunDuration = 1;
    }
}
