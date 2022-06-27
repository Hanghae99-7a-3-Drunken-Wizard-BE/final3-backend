package com.example.game.Game.card.magic.enchantment;

import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ENCHANTMENT;

public class PartyHeal extends Card {
    public PartyHeal(){
        this.cardName = "Party Heal";
        this.description = "무시무시한 결튀장 위에서도 박애주의가 꽃필 수 있을까요? 마법으로 대상을 지료합니다.";
        this.cardType = ENCHANTMENT;
        this.target = Target.ALLY;
        this.healthModifier = 3;
        this.manaCost = -6;
    }
}
