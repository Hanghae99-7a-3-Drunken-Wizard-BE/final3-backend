package com.example.game.Game.card.item;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Deck;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ITEM;

public class Panacea extends Card {

    public Panacea(Deck deck) {
        this.gameDeck = deck;
        this.cardName = "Panacea";
        this.description = "원래 살충제를 만드려고 했지만 거하게 실패했습니다. 대신 온갖 상태이상을 치료하는 약이 완성되었죠. 모든 상태이상을 제거합니다. 먹을수 있다면 말이죠";
        this.cardType = ITEM;
        this.target = Target.SELECT;
        this.poisonDuration = 0;
        this.stunDuration = 0;
        this.muteDuration = 0;
        this.sleepDuration = 0;
        this.onHand = 0L;
    }
}
