package com.example.game.Game.card.item;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ITEM;

public class LeftoverOctopus extends Card {

    public LeftoverOctopus(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
        this.cardName = "Leftover Octopus";
        this.description = "질겅질겅 씹다보면 좀 체력이 오르는 기분이 듭니다. 데비존스의 수염이라는 소문이 있습니다. 체력을 회복합니다.";
        this.cardType = ITEM;
        this.target = Target.ME;
        this.healthModifier = 2;
        this.onHand = 0L;
    }
}
