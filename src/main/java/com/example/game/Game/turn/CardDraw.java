package com.example.game.Game.turn;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
@RequiredArgsConstructor
public class CardDraw {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public void CardDraw(Long gameRoomId, Long playerId){
        GameRoom gameRoom = gameRepository.findByGameRoomId(gameRoomId);
        Player player = playerRepository.findById(playerId).orElseThrow(()-> new NullPointerException("플레이어 없음"));

    }
}

