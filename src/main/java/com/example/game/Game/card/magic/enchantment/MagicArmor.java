package com.example.game.Game.card.magic.enchantment;

import com.example.game.Game.Game;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ENCHANTMENT;

public class MagicArmor extends Card {

    public MagicArmor(Game game) {
        this.game = game;
        this.cardName = "Magic Armor";
        this.description = "전투용마법이지만 실생활에도 요긴합니다. 얼굴로 날아오는 맥주잔 정도는 무시가 가능합니다. 방어력을 획득합니다.";
        this.cardType = ENCHANTMENT;
        this.target = Target.SELECT;
        this.manaCost = -2;
        this.weakDuration = -3;
    }
}
