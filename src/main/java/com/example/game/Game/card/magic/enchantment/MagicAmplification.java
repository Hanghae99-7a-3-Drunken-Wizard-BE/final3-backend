package com.example.game.Game.card.magic.enchantment;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ENCHANTMENT;

public class MagicAmplification extends Card {
    public MagicAmplification(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
        this.cardName = "Magic Amplification";
        this.description = "진짜 이제 안봐준다 라는 중얼거림은 구차하지만 마법사에겐 의외로 효과가 있습니다. 공격마법의 효과를 강화합니다.";
        this.cardType = ENCHANTMENT;
        this.target = Target.SELECT;
        this.manaCost = -2;
        this.damageModifierDuration = 3;
    }
}
