package com.example.game.Game.card.magic.curse;

import com.example.game.Game.Game;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.CURSE;

public class Venom extends Card {

    public Venom(Game game) {
        this.game = game;
        this.cardName = "Venom";
        this.description = "돈빌려갔다가 갚지 않은 친구를 생각하며 특별히 만든 중독 마법입니다. 효과가 오래가는 독에 중독시킵니다.";
        this.cardType = CURSE;
        this.target = Target.SELECT;
        this.manaCost = -3;
        this.poisonDuration = 5;
    }
}
