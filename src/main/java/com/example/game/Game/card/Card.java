package com.example.game.Game.card;

import com.example.game.Game.GameRoom;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column
    public String cardName;

    @Column
    public String description;

    @Column
    public CardType cardType;

    @Column
    public Target target;

    @Column
    public int manaCost;

    @Column
    public int healthModifier;

    @Column
    public Boolean Shield;

    @Column
    public int manaModifier;

    @Column
    public int poisonDuration;

    @Column
    public int stunDuration;

    @Column
    public int muteDuration;

    @Column
    public int petrifyDuration;

    @Column
    public int weakDuration;

    @Column
    public int damageModifierDuration;

    @Column
    public int manaCostModifierDuration;

    @Column
    public Integer sleepDuration;

    @OneToOne
    @JoinColumn
    public SpecialEffect specialEffect;

    @ManyToOne
    @JoinColumn(name = "gameRoom_Id")
    public GameRoom gameRoom;

}
