package com.example.game.Game.card.magic.curse;

import com.example.game.Game.Game;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.CURSE;

public class Yfeputs extends Card {

    public Yfeputs(Game game) {
        this.game = game;
        this.cardName = "Yfeputs";
        this.description = "술취한 상태에서 발음하기엔 좀 어렵지만 어차피 맞는 쪽도 술취해서 어떻게든 됩니다. 상대를 기절시킵니다.";
        this.cardType = CURSE;
        this.target = Target.SELECT;
        this.manaCost = -3;
        this.stunDuration = 1;
    }
}
