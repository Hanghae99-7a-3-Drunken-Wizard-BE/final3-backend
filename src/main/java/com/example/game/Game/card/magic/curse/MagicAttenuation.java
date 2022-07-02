package com.example.game.Game.card.magic.curse;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.CURSE;

public class MagicAttenuation extends Card {
    public MagicAttenuation(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
        this.cardName = "Magic Attenuation";
        this.description = "안그래도 헤롱거리는 적에게 간단한 방해공작 하나면 적의 마법 효과를 감소시킬 수 있습니다. 대상이 입히는 데미지를 감소시킵니다.";
        this.cardType = CURSE;
        this.target = Target.SELECT;
        this.manaCost = -2;
        this.damageModifierDuration = -3;
    }
}
