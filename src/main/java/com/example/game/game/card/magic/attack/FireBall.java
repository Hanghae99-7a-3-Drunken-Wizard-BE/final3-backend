package com.example.game.game.card.magic.attack;

import com.example.game.game.card.Card;
import com.example.game.game.card.Target;

import static com.example.game.game.card.CardType.ATTACK;

public class FireBall extends Card {
    public FireBall() {
        this.cardName = "Fire Ball";
        this.description = "불타는 구야 말로 마법사들의 로망, 한번의 폭발에 모든 적이 휩쓸립니다. 모든 적에게 적은 피해를 입힙니다.";
        this.cardType = ATTACK;
        this.target = Target.ENEMY;
        this.healthModifier = -2;
        this.manaCost = -4;
    }
}
