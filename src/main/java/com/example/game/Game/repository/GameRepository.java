package com.example.game.Game.repository;

import com.example.game.Game.h2Package.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, String> {
    Game findByRoomId(String RoomId);
}
