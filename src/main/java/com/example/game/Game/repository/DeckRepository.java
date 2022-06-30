package com.example.game.Game.repository;

import com.example.game.Game.card.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Deck , Long> {
}
