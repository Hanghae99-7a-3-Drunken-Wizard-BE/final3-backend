package com.example.game.Game.turn;

import com.example.game.Game.h2Package.Game;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.h2Package.Player;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.PlayerRepository;
import com.example.game.Game.h2Package.GameRoom;
import com.example.game.Game.repository.GameRoomRepository;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EndGame {

    private final GameRepository gameRepository;
    private final GameRoomRepository gameRoomRepository;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final JsonStringBuilder jsonStringBuilder;

    @Transactional("gameTransactionManager")
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
                User user1 = userRepository.findById(team2.get(0).getPlayerId()).orElseThrow(()->new NullPointerException("유저없음"));
                User user2 = userRepository.findById(team2.get(1).getPlayerId()).orElseThrow(()->new NullPointerException("유저없음"));
                User user3 = userRepository.findById(team1.get(0).getPlayerId()).orElseThrow(()->new NullPointerException("유저없음"));
                User user4 = userRepository.findById(team1.get(1).getPlayerId()).orElseThrow(()->new NullPointerException("유저없음"));
                user1.setWinCount(user1.getWinCount() + 1);
                user2.setWinCount(user2.getWinCount() + 1);
                user3.setLoseCount(user3.getLoseCount() + 1);
                user4.setLoseCount(user4.getLoseCount() + 1);
                userRepository.save(user1);
                userRepository.save(user2);
                userRepository.save(user3);
                userRepository.save(user4);
                return jsonStringBuilder.endGameResponseDtoJsonBuilder(false);
            } else if (team2Eliminated) {
                User user1 = userRepository.findById(team1.get(0).getPlayerId()).orElseThrow(()->new NullPointerException("유저없음"));
                User user2 = userRepository.findById(team1.get(1).getPlayerId()).orElseThrow(()->new NullPointerException("유저없음"));
                User user3 = userRepository.findById(team2.get(0).getPlayerId()).orElseThrow(()->new NullPointerException("유저없음"));
                User user4 = userRepository.findById(team2.get(1).getPlayerId()).orElseThrow(()->new NullPointerException("유저없음"));
                user1.setWinCount(user1.getWinCount() + 1);
                user2.setWinCount(user2.getWinCount() + 1);
                user3.setLoseCount(user3.getLoseCount() + 1);
                user4.setLoseCount(user4.getLoseCount() + 1);
                userRepository.save(user1);
                userRepository.save(user2);
                userRepository.save(user3);
                userRepository.save(user4);
                return jsonStringBuilder.endGameResponseDtoJsonBuilder(true);
            } else {
                throw new IllegalArgumentException("게임 진행중");
            }
        }
    }

}
