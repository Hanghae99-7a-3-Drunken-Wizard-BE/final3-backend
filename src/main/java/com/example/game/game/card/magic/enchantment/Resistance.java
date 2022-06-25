package com.example.game.game.card.magic.enchantment;

import com.example.game.game.card.Card;
import com.example.game.game.card.Target;

import static com.example.game.game.card.CardType.ENCHANTMENT;

public class Resistance extends Card {
    public Resistance() {
        this.cardName = "Remedy";
        this.description = "허약하기 짝이없는 마법사들을 위한 자구책, 상태이상을 제거하거나 상태이상 저항력을 높입니다.";
        this.cardType = ENCHANTMENT;
        this.target = Target.SELECT;
        this.manaCost = -2;
        this.poisonDuration = -2;
        this.stunDuration = -2;
        this.muteDuration = -2;
        this.petrifyDuration = -2;
    }
}


//    public int poisonDuration;
//
//    public int stunDuration;
//
//    public int muteDuration;
//
//    public int petrifyDuration;