package com.example.game.Game.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Card {

    public String cardName;

    public String description;

    public CardType cardType;

    public Target target;

    public int manaCost;

    public int healthModifier;

    public Boolean Shield;

    public int damageModifier;

    public int manaModifier;

    public int poisonDuration;

    public int stunDuration;

    public int muteDuration;

    public int petrifyDuration;

    public SpecialEffect specialEffect;

}
