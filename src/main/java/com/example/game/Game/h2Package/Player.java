package com.example.game.Game.h2Package;

import com.example.game.Game.player.CharactorClass;
import com.example.game.model.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "player")
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Player {

    @Id
    private Long playerId;

    @Column
    private String username;

    @Column
    private boolean team;

    @Column
    private int turnOrder;

    @Column
    private CharactorClass charactorClass;

    @Column
    private int health;

    @Column
    private boolean shield;

    @Column
    private int mana;

    @Column
    private boolean dead;

    @Column
    private int poisonedDuration;

    @Column
    private int stunnedDuration; // 기절한

    @Column
    private int mutedDuration;

    @Column
    private int petrifiedDuration; // 석화된

    @Column
    public int damageModifierDuration; //시전자 관련 데미지 증감(음수일 때 공격력 약화, 양수일때 공격력 증폭)
    //(어플라이카드투캐릭터 메소드 내에서 개별처리)

    @Column
    public int weakDuration; //피시전자 관련 데미지 증감 (음수일때 아머, 양수일떄 약점노출)
    //(플레이어.스테이터스업데이트 단에서 처리)

    @Column
    public int manaCostModifierDuration;

    @Column
    private int sleepDuration;

    @ManyToOne
    @JoinColumn(name = "room_Id")
    private Game game;

    public Player(User user, Game game){
        this.playerId = user.getId();
        this.username = user.getNickname();
        this.health = 20;
        this.mana = 10;
        this.shield = false;
        this.dead = false;
        this.poisonedDuration = 0;
        this.stunnedDuration = 0;
        this.mutedDuration = 0;
        this.petrifiedDuration= 0;
        this.damageModifierDuration = 0;
        this.manaCostModifierDuration = 0;
        this.sleepDuration = 0;
        this.weakDuration = 0;
        this.game = game;
    }

    //2중 3항 연산자로 구성되어 있다 - 이는 상태이상에 대한 저항력 획득과 치료를 구분하기 위해서이다.
    public void applyAdditionalEffect(Card card){
        if (card.getCardName().equals("Dispel")) {
            this.poisonedDuration = 0;
            this.stunnedDuration = 0;
            this.mutedDuration = 0;
            this.petrifiedDuration = 0;
            this.damageModifierDuration = 0;
            this.weakDuration = 0;
            this.manaCostModifierDuration = 0;
            this.sleepDuration = 0;
        } else {
            this.poisonedDuration = (card.getPoisonDuration() != null) ?
                    ((card.getPoisonDuration() == 0 && this.getPoisonedDuration() > 0) ? 0 : this.poisonedDuration + card.getPoisonDuration()) : this.poisonedDuration;
            this.stunnedDuration = (card.getStunDuration() != null) ?
                    ((card.getStunDuration() == 0 && this.getStunnedDuration() > 0) ? 0 : this.stunnedDuration + card.getStunDuration()) : this.stunnedDuration;
            this.mutedDuration = (card.getMuteDuration() != null) ?
                    ((card.getMuteDuration() == 0 && this.getMutedDuration() > 0) ? 0 : this.mutedDuration + card.getMuteDuration()) : this.mutedDuration;
            this.petrifiedDuration = (card.getPetrifyDuration() != null) ?
                    ((card.getPetrifyDuration() == 0 && this.getPetrifiedDuration() > 0) ? 0 : this.petrifiedDuration + card.getPetrifyDuration()) : this.petrifiedDuration;
            this.damageModifierDuration = (card.getDamageModifierDuration() != null) ?
                    ((card.getDamageModifierDuration() == 0 && this.getDamageModifierDuration() < 0) ? 0 : this.damageModifierDuration + card.getDamageModifierDuration()) : this.damageModifierDuration;
            this.weakDuration = (card.getWeakDuration() != null) ?
                    ((card.getWeakDuration() == 0) ? 0 : this.weakDuration + card.getWeakDuration()) : this.weakDuration;
            this.manaCostModifierDuration = (card.getManaCostModifierDuration() != null) ?
                    ((card.getManaCostModifierDuration() == 0 && this.getManaCostModifierDuration() < 0) ? 0 : this.manaCostModifierDuration + card.getManaCostModifierDuration()) : this.manaCostModifierDuration;
            this.sleepDuration = (card.getSleepDuration() != null) ?
                    ((card.getSleepDuration() == 0 && this.getSleepDuration() > 0) ? 0 : this.sleepDuration + card.getSleepDuration()) : this.sleepDuration;
        }
    }

    public void durationDecrease() {
        if (this.poisonedDuration > 0) {this.poisonedDuration -= 1;}
        if (this.stunnedDuration > 0) {this.stunnedDuration -= 1;}
        if (this.mutedDuration > 0) {this.mutedDuration -= 1;}
        if (this.petrifiedDuration > 0) {this.petrifiedDuration -= 1;}
        if (this.damageModifierDuration > 0) {this.damageModifierDuration -= 1;}
        if (this.weakDuration > 0) {this.weakDuration -= 1;}
        if (this.manaCostModifierDuration > 0) {this.manaCostModifierDuration -= 1;}
        if (this.sleepDuration > 0) {this.sleepDuration -= 1;}
        if (this.damageModifierDuration < 0) {this.damageModifierDuration += 1;}
        if (this.manaCostModifierDuration < 0) {this.manaCostModifierDuration += 1;}
        if (this.weakDuration < 0) {this.weakDuration += 1;}
        System.out.println(this.mana);
        if (this.mana < 10) {this.mana += Math.min(10-this.mana,3);}
        System.out.println(this.mana);
    }

    public void applyHealerHealWithDamageModifierPositive() {
        if(this.petrifiedDuration <= 0) {
            this.health += 4;
            this.poisonedDuration = 0;}
    }

    public void applyHealerHealWithDamageModifierNegative() {
        if(this.petrifiedDuration <= 0) {
            this.health += 2;
            this.poisonedDuration = 0;}
    }

    public void applyHealerHeal() {
        if(this.petrifiedDuration <= 0) {
            this.health += 3;
            this.poisonedDuration = 0;}
    }

    public void addOnHand(Card card){
        card.setLyingPlace(this.playerId);
    }

    public void applyPoison() {this.health += (this.poisonedDuration > 0) ? -1 : 0;}

    public void applySleepRegeneration() {
        this.health += (this.sleepDuration > 0) ? +1 : 0;
    }

    public void applyhealerHealManaCost() {
        this.mana -= 4;
    }

    public void applyhealerHealManaCostWithModifierPositive() {
        this.mana -= 3;
    }

    public void applyhealerHealManaCostWithModifierNegative() {
        this.mana -= 5;
    }

    public void addMana() {
        this.mana += 1;
    }
}
