package com.example.game.Game.card.magic.curse;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.CURSE;

public class Sleep extends Card {

    public Sleep(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
        this.cardName = "Sleep";
        this.description = "술취해서 소동을 부리는 사람은 재우는게 최고죠. 대상을 재웁니다. 수면중일때 데미지를 받으면 깹니다. 잠들어 있는 동안 체력이 회복됩니다";
        this.cardType = CURSE;
        this.target = Target.SELECT;
        this.manaCost = -3;
        this.sleepDuration = 5;
        this.stunDuration = 0;
        this.muteDuration = 0;
    }
}
