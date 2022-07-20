package com.example.game.websocket;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameRoomRepository extends JpaRepository<GameRoom, Long> {
    Page<GameRoom> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<GameRoom> findByRoomNameContaining(String keyword, Pageable pageable);
    GameRoom findByRoomId(String roomId);
}