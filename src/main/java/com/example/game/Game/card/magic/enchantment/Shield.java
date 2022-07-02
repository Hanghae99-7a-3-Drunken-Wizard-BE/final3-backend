package com.example.game.Game.card.magic.enchantment;

<<<<<<< HEAD
import com.example.game.Game.GameRoom;
=======
>>>>>>> b91063f6740ff4b828df08d08e283578356e4079
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ENCHANTMENT;

public class Shield extends Card {
<<<<<<< HEAD
    public Shield(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
=======
    public Shield() {
>>>>>>> b91063f6740ff4b828df08d08e283578356e4079
        this.cardName = "Shield";
        this.description = "공격이 최선의 방어구라요? 세상엔 동귀어진이라는 것도 있습니다. 나를 타겟팅한 한개의 마법을 무력화합니다.";
        this.cardType = ENCHANTMENT;
        this.target = Target.SELECT;
        this.Shield = true;
        this.manaCost = -2;
    }
}
