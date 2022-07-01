package com.example.game.Game.repository;

import com.example.game.Game.GameRoom;
import com.example.game.Game.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByGameRoom(GameRoom gameRoom);
    List<Player> findByGameRoomAndTeam(GameRoom gameRoom, boolean team);
    Player findByGameRoomAndTurnOrder(GameRoom gameRoom, int turnOrder);
}
