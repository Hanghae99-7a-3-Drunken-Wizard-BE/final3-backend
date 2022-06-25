package com.example.game.Game.card.magic.curse;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.CURSE;

public class Petrification extends Card {
    public Petrification(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
        this.cardName = "Petrification";
        this.description = "마법사와 시비를 붙는걸 보면 돌머리가 의심됩니다. 돌은 돌로. 적을 돌로 만듭니다.";
        this.cardType = CURSE;
        this.target = Target.SELECT;
        this.manaCost = -3;
        this.petrifyDuration = 2;
    }
}
