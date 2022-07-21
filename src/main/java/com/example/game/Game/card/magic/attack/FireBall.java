package com.example.game.Game.card.magic.attack;

import com.example.game.Game.h2Package.Game;
import com.example.game.Game.h2Package.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ATTACK;

public class FireBall extends Card {
    public FireBall(Game game) {
        this.game = game;
        this.cardName = "Fire Ball";
        this.description = "불타는 구야 말로 마법사들의 로망, 한번의 폭발에 모든 적이 휩쓸립니다. 모든 적에게 적은 피해를 입힙니다.";
        this.cardType = ATTACK;
        this.target = Target.ENEMY;
        this.healthModifier = -2;
        this.manaCost = -4;
        this.sleepDuration = 0;
    }
}
