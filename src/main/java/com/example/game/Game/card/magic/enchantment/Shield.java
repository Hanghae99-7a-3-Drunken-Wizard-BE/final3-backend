package com.example.game.Game.card.magic.enchantment;

import com.example.game.Game.Game;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ENCHANTMENT;

public class Shield extends Card {

    public Shield(Game game) {
        this.game = game;
        this.cardName = "Shield";
        this.description = "공격이 최선의 방어구라요? 세상엔 동귀어진이라는 것도 있습니다. 나를 타겟팅한 한개의 마법을 무력화합니다.";
        this.cardType = ENCHANTMENT;
        this.target = Target.SELECT;
        this.shield = true;
        this.manaCost = -2;
    }
}
