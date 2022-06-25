package com.example.game.Game.card;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class SpecialEffect {

    @Id
    @GeneratedValue
    private Long id;
}
