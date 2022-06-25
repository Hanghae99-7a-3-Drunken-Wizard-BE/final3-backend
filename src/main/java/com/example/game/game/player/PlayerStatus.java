package com.example.game.game.player;

import com.example.game.game.card.Card;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlayerStatus {

    private int health;

    private boolean shield;

    private int mana;

    private boolean dead;

    private int poisonedDuration;

    private int stunnedDuration; // 기절한

    private int mutedDuration;

    private int weakenedDuration;

    private int petrifiedDuration; // 석화된

    private int enhencedDuration;

    public PlayerStatus() {
        this.health = 20;
        this.mana = 2;
        this.shield = false;
        this.dead = false;
        this.poisonedDuration = 0;
        this.stunnedDuration = 0;
        this.mutedDuration = 0;
        this.weakenedDuration = 0;
        this.petrifiedDuration= 0;
        this.enhencedDuration = 0;
    }

    public PlayerStatus(PlayerStatus playerStatus, Card card) {
        this.dead = playerStatus.getHealth() + card.getHealthModifier() <= 0;
        this.health = playerStatus.getHealth() + card.getHealthModifier();
        this.shield = card.getShield() != null;
        this.mana = playerStatus.getMana() + card.getManaModifier();
        this.poisonedDuration = playerStatus.getPoisonedDuration() + card.getPoisonDuration();
        this.stunnedDuration = playerStatus.getStunnedDuration() + card.getStunDuration();
        this.mutedDuration = playerStatus.getMutedDuration() + card.getMuteDuration();
        this.petrifiedDuration = playerStatus.getPetrifiedDuration() + card.getPetrifyDuration();
    }

    public PlayerStatus manaUse(PlayerStatus playerStatus, Card card) {
        playerStatus.setMana(playerStatus.getMana() + card.getManaCost());
        return playerStatus;
    }


}
