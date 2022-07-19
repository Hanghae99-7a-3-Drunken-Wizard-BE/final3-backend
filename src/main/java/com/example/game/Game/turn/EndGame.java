package com.example.game.Game.turn;

import com.example.game.Game.h2Package.Game;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.h2Package.Player;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.PlayerRepository;
import com.example.game.Game.h2Package.GameRoom;
import com.example.game.Game.repository.GameRoomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EndGame {

    private final GameRepository gameRepository;
    private final GameRoomRepository gameRoomRepository;
    private final PlayerRepository playerRepository;
    private final JsonStringBuilder jsonStringBuilder;

    @Transactional
    public String gameEnd(String roomId) throws JsonProcessingException {
        GameRoom gameRoom = gameRoomRepository.findByRoomId(roomId);
        Game game = gameRepository.findByRoomId(roomId);
        List<Player> team1 = playerRepository.findByGameAndTeam(game, true);
        List<Player> team2 = playerRepository.findByGameAndTeam(game, false);
        boolean team1Eliminated = team1.get(0).isDead() && team1.get(1).isDead();
        boolean team2Eliminated = team2.get(0).isDead() && team2.get(1).isDead();
        gameRepository.delete(game);
        gameRoomRepository.delete(gameRoom);
        if (team1Eliminated && team2Eliminated) {return jsonStringBuilder.endGameResponseDtoJsonBuilder(null);}
        else {
            if (team1Eliminated) {
                return jsonStringBuilder.endGameResponseDtoJsonBuilder(false);
            } else if (team2Eliminated) {
                return jsonStringBuilder.endGameResponseDtoJsonBuilder(true);
            } else {
                throw new IllegalArgumentException("게임 진행중");
            }
        }
    }

}
