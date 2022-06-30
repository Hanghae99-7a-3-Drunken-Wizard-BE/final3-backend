package com.example.game.Game.card;

import com.example.game.Game.GameRoom;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
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
    public Integer manaCost;

    @Column
    public Integer healthModifier;

    @Column
    public Integer manaModifier;

    @Column
    public Boolean Shield;

    @Column
    public Integer poisonDuration;

    @Column
    public Integer stunDuration;

    @Column
    public Integer muteDuration;

    @Column
    public Integer petrifyDuration;

    @Column
    public Integer weakDuration;

    @Column
    public Integer damageModifierDuration;

    @Column
    public Integer manaCostModifierDuration;

    @Column
    public Integer sleepDuration;

    @OneToOne
    @JoinColumn
    public SpecialEffect specialEffect;

    @ManyToOne
    @JoinColumn(name = "deck_Id")
    public Deck gameDeck;

    @Column
    public Long onHand;

}
