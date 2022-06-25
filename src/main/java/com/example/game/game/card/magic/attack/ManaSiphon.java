package com.example.game.game.card.magic.attack;

import com.example.game.game.card.Card;
import com.example.game.game.card.Target;

import static com.example.game.game.card.CardType.ATTACK;

public class ManaSiphon extends Card {
    public ManaSiphon() {
        this.cardName = "Mana Siphon";
        this.description = "마나는 언제나 달달합니다. 심지어 뺏어온 거라면 말해 뭐할까요. 적의 마나를 빼앗고 적은 피해를 입힙니다";
        this.cardType = ATTACK;
        this.target = Target.SELECT;
        this.healthModifier = -1;
        this.manaCost = 2;
        this.manaModifier = -3;
    }
}
