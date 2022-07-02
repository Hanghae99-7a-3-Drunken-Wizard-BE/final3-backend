package com.example.game.Game.card.magic.curse;

<<<<<<< HEAD
import com.example.game.Game.GameRoom;
=======
>>>>>>> b91063f6740ff4b828df08d08e283578356e4079
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.CURSE;

public class Petrification extends Card {
<<<<<<< HEAD
    public Petrification(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
=======
    public Petrification() {
>>>>>>> b91063f6740ff4b828df08d08e283578356e4079
        this.cardName = "Petrification";
        this.description = "마법사와 시비를 붙는걸 보면 돌머리가 의심됩니다. 돌은 돌로. 적을 돌로 만듭니다.";
        this.cardType = CURSE;
        this.target = Target.SELECT;
        this.manaCost = -3;
        this.petrifyDuration = 2;
<<<<<<< HEAD
        this.poisonDuration = 0;
        this.stunDuration = 0;
        this.sleepDuration = 0;
        this.muteDuration = 0;
        this.damageModifierDuration = 0;
        this.manaCostModifierDuration = 0;
=======
>>>>>>> b91063f6740ff4b828df08d08e283578356e4079
    }
}
