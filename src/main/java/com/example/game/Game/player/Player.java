package com.example.game.Game.player;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.model.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column
    @OneToMany
    private List<Card> cardsOnHand = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "gameRoom_Id")
    private GameRoom gameRoom;

    public Player(User user, GameRoom gameRoom){
        this.username = user.getNickname();
        this.health = 20;
        this.mana = 20;
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
        this.gameRoom = gameRoom;
    }

    public void statusUpdate(Card card){
        if (this.weakDuration > 0) {this.health = (card.getHealthModifier()!= null) ?
                ((card.getHealthModifier()==0) ? 0 : this.health + card.getHealthModifier()-1) : this.health;}
        else if (this.weakDuration < 0) {this.health = (card.getHealthModifier()!= null) ?
                ((card.getHealthModifier()==0) ? 0 : this.health + card.getHealthModifier()+1) : this.health;}
        else{this.health = (card.getHealthModifier()!= null) ?
                ((card.getHealthModifier()==0) ? 0 : this.health + card.getHealthModifier()) : this.health;}
        this.shield = (card.getShield()!= null) ? (card.getShield()) : false;
        this.mana = (card.getManaModifier()!= null) ?
                ((card.getManaModifier()==0) ? 0 : this.mana + card.getManaModifier()) : this.mana;
        this.dead = this.health <= 0;
        this.poisonedDuration = (card.getPoisonDuration()!= null) ?
                ((card.getPoisonDuration()==0) ? 0 : this.poisonedDuration + card.getPoisonDuration()) : this.poisonedDuration;
        this.stunnedDuration = (card.getStunDuration()!= null) ?
                ((card.getStunDuration()==0) ? 0 : this.stunnedDuration + card.getStunDuration()) : this.stunnedDuration;
        this.mutedDuration = (card.getMuteDuration()!= null) ?
                ((card.getMuteDuration()==0) ? 0 : this.mutedDuration + card.getMuteDuration()) : this.mutedDuration;
        this.petrifiedDuration = (card.getPetrifyDuration()!= null) ?
                ((card.getPetrifyDuration()==0) ? 0 : this.petrifiedDuration + card.getPetrifyDuration()) : this.petrifiedDuration;
        this.damageModifierDuration = (card.getDamageModifierDuration()!= null) ?
                ((card.getDamageModifierDuration()==0) ? 0 : this.damageModifierDuration + card.getDamageModifierDuration()) : this.damageModifierDuration;
        this.weakDuration = (card.getWeakDuration()!= null) ?
                ((card.getWeakDuration()==0) ? 0 : this.weakDuration + card.getWeakDuration()) : this.weakDuration;
        this.manaCostModifierDuration = (card.getManaCostModifierDuration()!= null) ?
                ((card.getManaCostModifierDuration()==0) ? 0 : this.manaCostModifierDuration + card.getManaCostModifierDuration()) : this.manaCostModifierDuration;
        this.sleepDuration = (card.getSleepDuration()!= null) ?
                ((card.getSleepDuration()==0) ? 0 : this.sleepDuration + card.getSleepDuration()) : this.sleepDuration;
    }

    public void statusUpdateWithDamageModifierPositive(Card card){
        if (this.weakDuration > 0) {this.health = (card.getHealthModifier()!= null) ?
                ((card.getHealthModifier()==0) ? 0 : this.health + card.getHealthModifier()-2) : this.health;}
        else if (this.weakDuration < 0) {this.health = (card.getHealthModifier()!= null) ?
                ((card.getHealthModifier()==0) ? 0 : this.health + card.getHealthModifier()) : this.health;}
        else{this.health = (card.getHealthModifier()!= null) ?
                ((card.getHealthModifier()==0) ? 0 : this.health + card.getHealthModifier()-1) : this.health;}
        this.shield = (card.getShield()!= null) ? (card.getShield()) : false;
        this.mana = (card.getManaModifier()!= null) ?
                ((card.getManaModifier()==0) ? 0 : this.mana + card.getManaModifier()-1) : this.mana;;
        this.dead = this.health <= 0;
        this.poisonedDuration = (card.getPoisonDuration()!= null) ?
                ((card.getPoisonDuration()==0) ? 0 : this.poisonedDuration + card.getPoisonDuration()) : this.poisonedDuration;
        this.stunnedDuration = (card.getStunDuration()!= null) ?
                ((card.getStunDuration()==0) ? 0 : this.stunnedDuration + card.getStunDuration()) : this.stunnedDuration;
        this.mutedDuration = (card.getMuteDuration()!= null) ?
                ((card.getMuteDuration()==0) ? 0 : this.mutedDuration + card.getMuteDuration()) : this.mutedDuration;
        this.petrifiedDuration = (card.getPetrifyDuration()!= null) ?
                ((card.getPetrifyDuration()==0) ? 0 : this.petrifiedDuration + card.getPetrifyDuration()) : this.petrifiedDuration;
        this.damageModifierDuration = (card.getDamageModifierDuration()!= null) ?
                ((card.getDamageModifierDuration()==0) ? 0 : this.damageModifierDuration + card.getDamageModifierDuration()) : this.damageModifierDuration;
        this.weakDuration = (card.getWeakDuration()!= null) ?
                ((card.getWeakDuration()==0) ? 0 : this.weakDuration + card.getWeakDuration()) : this.weakDuration;
        this.manaCostModifierDuration = (card.getManaCostModifierDuration()!= null) ?
                ((card.getManaCostModifierDuration()==0) ? 0 : this.manaCostModifierDuration + card.getManaCostModifierDuration()) : this.manaCostModifierDuration;
        this.sleepDuration = (card.getSleepDuration()!= null) ?
                ((card.getSleepDuration()==0) ? 0 : this.sleepDuration + card.getSleepDuration()) : this.sleepDuration;
    }

    public void statusUpdateWithDamageModifierNegative(Card card){
        if (this.weakDuration > 0) {this.health = (card.getHealthModifier()!= null) ?
                ((card.getHealthModifier()==0) ? 0 : this.health + card.getHealthModifier()) : this.health;}
        else if (this.weakDuration < 0) {this.health = (card.getHealthModifier()!= null) ?
                ((card.getHealthModifier()==0) ? 0 : this.health + card.getHealthModifier()+2) : this.health;}
        else{this.health = (card.getHealthModifier()!= null) ?
                ((card.getHealthModifier()==0) ? 0 : this.health + card.getHealthModifier()+1) : this.health;}
        this.shield = (card.getShield()!= null) ? (card.getShield()) : false;
        this.mana = (card.getManaModifier()!= null) ?
                ((card.getManaModifier()==0) ? 0 : this.mana + card.getManaModifier()+1) : this.mana;
        this.dead = this.health <= 0;
        this.poisonedDuration = (card.getPoisonDuration()!= null) ?
                ((card.getPoisonDuration()==0) ? 0 : this.poisonedDuration + card.getPoisonDuration()) : this.poisonedDuration;
        this.stunnedDuration = (card.getStunDuration()!= null) ?
                ((card.getStunDuration()==0) ? 0 : this.stunnedDuration + card.getStunDuration()) : this.stunnedDuration;
        this.mutedDuration = (card.getMuteDuration()!= null) ?
                ((card.getMuteDuration()==0) ? 0 : this.mutedDuration + card.getMuteDuration()) : this.mutedDuration;
        this.petrifiedDuration = (card.getPetrifyDuration()!= null) ?
                ((card.getPetrifyDuration()==0) ? 0 : this.petrifiedDuration + card.getPetrifyDuration()) : this.petrifiedDuration;
        this.damageModifierDuration = (card.getDamageModifierDuration()!= null) ?
                ((card.getDamageModifierDuration()==0) ? 0 : this.damageModifierDuration + card.getDamageModifierDuration()) : this.damageModifierDuration;
        this.weakDuration = (card.getWeakDuration()!= null) ?
                ((card.getWeakDuration()==0) ? 0 : this.weakDuration + card.getWeakDuration()) : this.weakDuration;
        this.manaCostModifierDuration = (card.getManaCostModifierDuration()!= null) ?
                ((card.getManaCostModifierDuration()==0) ? 0 : this.manaCostModifierDuration + card.getManaCostModifierDuration()) : this.manaCostModifierDuration;
        this.sleepDuration = (card.getSleepDuration()!= null) ?
                ((card.getSleepDuration()==0) ? 0 : this.sleepDuration + card.getSleepDuration()) : this.sleepDuration;
    }

    public void applyHeal(Card card){
        this.health += card.getHealthModifier();
        this.poisonedDuration = 0;
    }

    public void applyHealWithDamageModifierPositive(Card card){
        this.health += card.getHealthModifier() +1;
        this.poisonedDuration = 0;
    }

    public void applyHealWithDamageModifierNegative(Card card){
        this.health += card.getHealthModifier() -1;
    }

    public void applyManaCost(Card card) {
        this.mana = (card.getManaCost()!= null) ?
                ((card.getManaCost()==0) ? 0 : this.mana + card.getManaCost()) : this.mana;
    }

    public void applyManaCostWithModifierPositive(Card card) {
        this.mana = (card.getManaCost()!= null) ?
                ((card.getManaCost()==0) ? 0 : this.mana + card.getManaCost()+1) : this.mana;
    }

    public void applyManaCostWithModifierNegative(Card card) {
        this.mana = (card.getManaCost()!= null) ?
                ((card.getManaCost()==0) ? 0 : this.mana + card.getManaCost()-1) : this.mana;
    }

    public void applyHealManaCostForHealer(Card card) {
        this.mana = (card.getManaCost()!= null) ?
                ((card.getManaCost()==0) ? 0 : this.mana + card.getManaCost()+1) : this.mana;
    }

    public void applyHealManaCostWithModifierPositiveForHealer(Card card) {
        this.mana = (card.getManaCost()!= null) ?
                ((card.getManaCost()==0) ? 0 : this.mana + card.getManaCost()+2) : this.mana;
    }

    public void applyHealManaCostWithModifierNegativeForHealer(Card card) {
        this.mana = (card.getManaCost()!= null) ?
                ((card.getManaCost()==0) ? 0 : this.mana + card.getManaCost()) : this.mana;
    }

    @Transactional
    public void addOnHand(Card card){
        this.cardsOnHand.add(card);
    }

    @Transactional
    public void removeFromHand(Card card){
        this.cardsOnHand.remove(card);
    }

}
