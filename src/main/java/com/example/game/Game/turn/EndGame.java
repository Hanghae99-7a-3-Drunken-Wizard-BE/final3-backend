package com.example.game.Game.turn;

import com.example.game.Game.Game;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.PlayerRepository;
import com.example.game.websocket.GameRoom;
import com.example.game.websocket.GameRoomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EndGame {

    private final GameRepository gameRepository;
    private final GameRoomRepository gameRoomRepository;
    private final PlayerRepository playerRepository;
    private final JsonStringBuilder jsonStringBuilder;

    public String gameEnd(String roomId) throws JsonProcessingException {
        GameRoom gameRoom = gameRoomRepository.findByRoomId(roomId);
        Game game = gameRepository.findByRoomId(roomId);
//        List<Player> team1 = playerRepository.findByGameAndTeam(game, true);
//        List<Player> team2 = playerRepository.findByGameAndTeam(game, false);
//        boolean team1Eliminated = team1.get(0).isDead() && team1.get(1).isDead();
//        boolean team2Eliminated = team2.get(0).isDead() && team2.get(1).isDead();
        gameRepository.delete(game);
        return "게임 강제 종료";
//        gameRoomRepository.delete(gameRoom);
//        if (team1Eliminated && team2Eliminated) {return jsonStringBuilder.endGameResponseDtoJsonBuilder(null);}
//        else {
//            if (team1Eliminated) {
//                return jsonStringBuilder.endGameResponseDtoJsonBuilder(false);
//            } else if (team2Eliminated) {
//                return jsonStringBuilder.endGameResponseDtoJsonBuilder(true);
//            } else {
//                throw new IllegalArgumentException("게임 진행중");
//            }
//        }
    }

}
