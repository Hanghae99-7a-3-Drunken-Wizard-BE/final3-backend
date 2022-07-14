package com.example.game.Game.turn;

import com.example.game.Game.gameDataDto.JsonStringBuilder;
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
    public String EndTurnCheck(Long playerId) throws JsonProcessingException {
        Player player = playerRepository.findById(playerId).orElseThrow(
                ()->new NullPointerException("플레이어 없음")
        );
        int order = player.getTurnOrder();
        for (int i = 0; i < 4; i++) {
            order = (order == 4) ? 1 : order+1;
            if (!playerRepository.findByGameAndTurnOrder(player.getGame(),order).isDead()) {
                break;
            }
        }
        player.durationDecrease();
        Player nextPlayer = playerRepository.findByGameAndTurnOrder(player.getGame(), order);
        return jsonStringBuilder.endTurnResponseDtoJsonBuilder(player, nextPlayer);

    }
}
