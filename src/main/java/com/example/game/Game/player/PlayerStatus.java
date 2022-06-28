//package com.example.game.Game.player;
//
//import com.example.game.Game.card.Card;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Setter
//@Getter
//public class PlayerStatus {
//
//
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//
//@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column
//    private int health;
//
//    @Column
//    private boolean shield;
//
//    @Column
//    private int mana;
//
//    @Column
//    private boolean dead;
//
//    @Column
//    private int poisonedDuration;
//
//    @Column
//    private int stunnedDuration; // 기절한
//
//    @Column
//    private int mutedDuration;
//
//    @Column
//    private int weakenedDuration;
//
//    @Column
//    private int petrifiedDuration; // 석화된
//
//    @Column
//    private int enhencedDuration;
//
//    @Column
//    private int sleepDuration;
//
//    public PlayerStatus() {
//        this.health = 20;
//        this.mana = 2;
//        this.shield = false;
//        this.dead = false;
//        this.poisonedDuration = 0;
//        this.stunnedDuration = 0;
//        this.mutedDuration = 0;
//        this.weakenedDuration = 0;
//        this.petrifiedDuration= 0;
//        this.enhencedDuration = 0;
//        this.sleepDuration = 0;
//    }
//
//    public PlayerStatus(PlayerStatus playerStatus, Card card) {
//        this.dead = playerStatus.getHealth() + card.getHealthModifier() <= 0;
//        this.health = playerStatus.getHealth() + card.getHealthModifier();
//        this.shield = card.getShield() != null;
//        this.mana = playerStatus.getMana() + card.getManaModifier();
//        this.poisonedDuration = playerStatus.getPoisonedDuration() + card.getPoisonDuration();
//        this.stunnedDuration = playerStatus.getStunnedDuration() + card.getStunDuration();
//        this.mutedDuration = playerStatus.getMutedDuration() + card.getMuteDuration();
//        this.petrifiedDuration = playerStatus.getPetrifiedDuration() + card.getPetrifyDuration();
//        this.sleepDuration = (card.getSleepDuration()!= null) ?
//                ((card.sleepDuration==0) ? 0 : playerStatus.getSleepDuration() + card.getSleepDuration()) : 0;
//    }
//
//
//
//    public PlayerStatus manaUse(PlayerStatus playerStatus, Card card) {
//        playerStatus.setMana(playerStatus.getMana() + card.getManaCost());
//        return playerStatus;
//    }
//
//
//}
