package com.example.game.Game.card.magic.curse;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Deck;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.CURSE;

public class Venom extends Card {

    public Venom(Deck deck) {
        this.gameDeck = deck;
        this.cardName = "Venom";
        this.description = "돈빌려갔다가 갚지 않은 친구를 생각하며 특별히 만든 중독 마법입니다. 효과가 오래가는 독에 중독시킵니다.";
        this.cardType = CURSE;
        this.target = Target.SELECT;
        this.manaCost = -3;
        this.poisonDuration = 5;
        this.onHand = 0L;
    }
}
