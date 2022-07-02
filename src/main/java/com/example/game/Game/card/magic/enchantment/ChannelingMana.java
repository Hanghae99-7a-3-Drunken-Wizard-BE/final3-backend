package com.example.game.Game.card.magic.enchantment;

import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ENCHANTMENT;

public class ChannelingMana extends Card {
    public ChannelingMana() {
        this.cardName = "Channeling Mana";
        this.description = "마법사는 가끔 마법을 쓰기 위해 무리할 수도 있습니다. 체력을 마나로 전환합니다";
        this.cardType = ENCHANTMENT;
        this.target = Target.ME;
        this.healthModifier = -3;
        this.manaCost = 3;
    }
}
