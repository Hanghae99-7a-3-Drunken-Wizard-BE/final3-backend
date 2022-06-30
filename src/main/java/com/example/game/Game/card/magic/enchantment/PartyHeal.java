package com.example.game.Game.card.magic.enchantment;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Deck;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ENCHANTMENT;

public class PartyHeal extends Card {
    public PartyHeal(Deck deck) {
        this.gameDeck = deck;
        this.cardName = "Party Heal";
        this.description = "다른 플레이어들이 이 글을 좋아합니다. 모든 아군을 치료합니다.";
        this.cardType = ENCHANTMENT;
        this.target = Target.ALLY;
        this.healthModifier = 3;
        this.manaCost = -6;
        this.onHand = 0L;
    }
}
