package com.example.game.Game.service;

import com.example.game.Game.Game;
import com.example.game.Game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class GameCloser {
    private final GameRepository gameRepository;

    @Transactional
    public void closeGameRoom(String id) {
        Game game = gameRepository.findByRoomId(id);
        gameRepository.delete(game);
    }
}
