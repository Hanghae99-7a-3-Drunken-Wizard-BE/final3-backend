package com.example.game.Game.card.magic.enchantment;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ENCHANTMENT;

public class Dispel extends Card {

    public Dispel(GameRoom gameRoom){
        this.gameRoom = gameRoom;
        this.cardName = "Dispel";
        this.description = "쓸데 없는 마법에 잘 걸리는 마법사를 위해 태어난 마법입니다. 현재 유효한 모든 상태이상과 저항력을 제거합니다";
        this.cardType = ENCHANTMENT;
        this.target = Target.SELECT;
        this.manaCost = -1;
        this.setShield(false);
        this.poisonDuration = 0;
        this.stunDuration = 0;
        this.muteDuration = 0;
        this.petrifyDuration = 0;
        this.weakDuration = 0;
        this. damageModifierDuration = 0;
        this.manaCostModifierDuration = 0;
        this.sleepDuration = 0;
    }
}
