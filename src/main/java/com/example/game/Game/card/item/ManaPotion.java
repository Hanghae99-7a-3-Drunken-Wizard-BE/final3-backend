package com.example.game.Game.card.item;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ITEM;

public class ManaPotion extends Card {

    public ManaPotion(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
        this.cardName = "Mana Potion";
        this.description = "마시면 혀 끝이 파래지는 물약입니다. 대충 상상하는 그런 맛이 납니다. 마나를 회복합니다.";
        this.cardType = ITEM;
        this.target = Target.ME;
        this.manaModifier = 3;
    }
}
