package com.example.game.Game.card.magic.enchantment;

<<<<<<< HEAD
import com.example.game.Game.GameRoom;
=======
>>>>>>> b91063f6740ff4b828df08d08e283578356e4079
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ENCHANTMENT;

public class Resistance extends Card {
<<<<<<< HEAD
    public Resistance(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
=======
    public Resistance() {
>>>>>>> b91063f6740ff4b828df08d08e283578356e4079
        this.cardName = "Remedy";
        this.description = "허약하기 짝이없는 마법사들을 위한 자구책, 상태이상을 제거하거나 상태이상 저항력을 높입니다.";
        this.cardType = ENCHANTMENT;
        this.target = Target.SELECT;
<<<<<<< HEAD
        this.manaCost = -1;
        this.poisonDuration = -1;
        this.stunDuration = -1;
        this.muteDuration = -1;
        this.petrifyDuration = -1;
=======
        this.manaCost = -2;
        this.poisonDuration = -2;
        this.stunDuration = -2;
        this.muteDuration = -2;
        this.petrifyDuration = -2;
>>>>>>> b91063f6740ff4b828df08d08e283578356e4079
    }
}


//    public int poisonDuration;
//
//    public int stunDuration;
//
//    public int muteDuration;
//
//    public int petrifyDuration;