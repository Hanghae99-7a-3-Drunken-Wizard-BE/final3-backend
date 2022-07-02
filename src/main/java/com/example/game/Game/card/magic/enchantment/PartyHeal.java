package com.example.game.Game.card.magic.enchantment;

<<<<<<< HEAD
import com.example.game.Game.GameRoom;
=======
>>>>>>> b91063f6740ff4b828df08d08e283578356e4079
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ENCHANTMENT;

public class PartyHeal extends Card {
<<<<<<< HEAD
    public PartyHeal(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
        this.cardName = "Party Heal";
        this.description = "다른 플레이어들이 이 글을 좋아합니다. 모든 아군을 치료합니다.";
=======
    public PartyHeal(){
        this.cardName = "Party Heal";
        this.description = "무시무시한 결튀장 위에서도 박애주의가 꽃필 수 있을까요? 마법으로 대상을 지료합니다.";
>>>>>>> b91063f6740ff4b828df08d08e283578356e4079
        this.cardType = ENCHANTMENT;
        this.target = Target.ALLY;
        this.healthModifier = 3;
        this.manaCost = -6;
    }
}
