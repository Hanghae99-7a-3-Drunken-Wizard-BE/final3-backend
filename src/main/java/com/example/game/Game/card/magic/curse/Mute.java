package com.example.game.Game.card.magic.curse;

import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.CURSE;

public class Mute extends Card {
    public Mute() {
        this.cardName = "Mute";
        this.description = "이 마법은 시끄러운 옆사람을 입다물게 하는데 탁월합니다. 대상을 침묵시켜 마법의 시전을 방해합니다.";
        this.cardType = CURSE;
        this.target = Target.SELECT;
        this.manaCost = -3;
        this.muteDuration = 2;
    }
}
