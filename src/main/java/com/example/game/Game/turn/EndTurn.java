package com.example.game.Game.turn;

import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.gameDataDto.request.PlayerRequestDto;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.PlayerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class EndTurn {
    private final PlayerRepository playerRepository;
    private final JsonStringBuilder jsonStringBuilder;

    @Transactional
    public String EndTrunCheck(Long playerId) throws JsonProcessingException {
        Player player = playerRepository.findById(playerId).orElseThrow(
                ()->new NullPointerException("플레이어 없음")
        );
        int nextOrder = (player.getTurnOrder() == 4) ? 1 : player.getTurnOrder()+1;
        player.durationDecrease();
        Player nextPlayer = playerRepository.findByGameAndTurnOrder(player.getGame(), nextOrder);
        return jsonStringBuilder.endTurnResponseDtoJsonBuilder(player, nextPlayer);

    }
}
