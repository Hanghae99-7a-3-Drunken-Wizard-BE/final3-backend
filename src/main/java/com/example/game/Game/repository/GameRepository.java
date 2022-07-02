package com.example.game.Game.repository;

import com.example.game.Game.GameRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<GameRoom, String> {
    GameRoom findByGameRoomId(String gameRoomId);
    List<GameRoom> findByRoomNameContaining(String keyword);
}
