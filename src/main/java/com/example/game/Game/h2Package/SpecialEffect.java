package com.example.game.Game.h2Package;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "specialeffect")
public abstract class SpecialEffect {

    @Id
    @GeneratedValue
    private Long id;
}
