package com.example.game.Game.repository;

import com.example.game.Game.Game;
import com.example.game.Game.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByCardId(Long id);
    List<Card> findByCardName(String name);
    List<Card> findByGame(Game game);
    List<Card> findByLyingPlaceAndGame(long id, Game game);
    List<Card> findByLyingPlace(long id);
    List<Card> findByLyingPlaceAndGameOrderByCardOrderAsc(long id, Game game);
}
