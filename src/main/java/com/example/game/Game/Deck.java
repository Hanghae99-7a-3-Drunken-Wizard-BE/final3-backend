package com.example.game.Game;

import com.example.game.Game.card.Card;
import com.example.game.Game.card.magic.attack.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck {
    public LinkedList<Card> createGameDeck(){
        LinkedList<Card> gameDeck = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            gameDeck.add(new MagicMissile());
        }
        for (int i = 0; i < 6; i++) {
            gameDeck.add(new BoulderStrike());
        }
        for (int i = 0; i < 6; i++) {
            gameDeck.add(new DeathRay());
        }
        for (int i = 0; i < 6; i++) {
            gameDeck.add(new FireBall());
        }
        for (int i = 0; i < 6; i++) {
            gameDeck.add(new PoisonArrow());
        }
        for (int i = 0; i < 6; i++) {
            gameDeck.add(new ManaSiphon());
        }
        Collections.shuffle(gameDeck);
        return gameDeck;
    }

    public List<Card> createOblivion() {
        return new ArrayList<>();
    }
}
