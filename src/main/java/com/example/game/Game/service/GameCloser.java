package com.example.game.Game.service;

import com.example.game.Game.GameRoom;
import com.example.game.Game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class GameCloser {
    private final GameRepository gameRepository;

    @Transactional
    public void closeGameRoom(Long id) {
        GameRoom gameRoom = gameRepository.findByGameRoomId(id);
        gameRepository.delete(gameRoom);
    }
}
