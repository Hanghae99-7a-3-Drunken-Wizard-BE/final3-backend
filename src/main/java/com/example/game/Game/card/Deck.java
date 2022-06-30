package com.example.game.Game.card;

import com.example.game.Game.card.Card;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deckId;

    @Column
    @OneToMany(mappedBy = "gameDeck", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> gameDeck;
}
