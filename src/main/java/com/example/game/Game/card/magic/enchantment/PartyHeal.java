package com.example.game.Game.card.magic.enchantment;

import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ENCHANTMENT;

public class PartyHeal extends Card {
    public PartyHeal(){
        this.cardName = "Party Heal";
        this.description = "다른 플레이어들이 이 글을 좋아합니다. 모든 아군을 치료합니다.";
        this.cardType = ENCHANTMENT;
        this.target = Target.ALLY;
        this.healthModifier = 3;
        this.manaCost = -6;
    }
}
