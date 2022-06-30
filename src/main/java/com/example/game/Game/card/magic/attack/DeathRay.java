package com.example.game.Game.card.magic.attack;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Deck;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ATTACK;

public class DeathRay extends Card {
    public DeathRay(Deck deck) {
        this.gameDeck = deck;
        this.cardName = "Death Ray";
        this.description = "적에게 무자비한 죽음의 광선을 발사합니다. 단일대상에게 큰 피해를 입힙니다";
        this.cardType = ATTACK;
        this.target = Target.SELECT;
        this.healthModifier = -6;
        this.manaCost = -5;
        this.sleepDuration = 0;
        this.onHand = 0L;
    }
}
