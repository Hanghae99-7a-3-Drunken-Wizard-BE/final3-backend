package com.example.game.Game.repository;

import com.example.game.Game.GameRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameRoom, Long> {
    GameRoom findByGameRoomId(Long gameRoomId);
}
