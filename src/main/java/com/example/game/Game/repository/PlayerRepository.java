package com.example.game.Game.repository;

import com.example.game.Game.Game;
import com.example.game.Game.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByGame(Game game);
    List<Player> findByGameAndTeam(Game game, boolean team);
    Player findByGameAndTurnOrder(Game game, int turnOrder);
}
