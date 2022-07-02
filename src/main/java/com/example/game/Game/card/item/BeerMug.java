package com.example.game.Game.card.item;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ITEM;

public class BeerMug extends Card {
    public BeerMug (GameRoom gameRoom) {
        this.gameRoom = gameRoom;
        this.cardName = "Beer Mug";
        this.description = "급한대로 던진것 치고는 묵직한 맛이 있습니다. 잠든 친구를 깨울때 제격입니다. 대상에게 아주 적은 데미지를 입힙니다.";
        this.cardType = ITEM;
        this.target = Target.SELECT;
        this.healthModifier = -1;
        this.sleepDuration = 0;
    }
}
