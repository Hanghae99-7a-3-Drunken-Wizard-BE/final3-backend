package com.example.game.Game.service;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.magic.attack.*;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.GameRepository;
import com.example.game.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GameStarter {

    private final GameRepository gameRepository;

    @Transactional
    public GameRoom createGameRoom (List<User> userInLobby){
        GameRoom gameRoom = new GameRoom();
        LinkedList<Card> deck = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            deck.add(new MagicMissile(gameRoom));
        }
        for (int i = 0; i < 6; i++) {
            deck.add(new BoulderStrike(gameRoom));
        }
        for (int i = 0; i < 6; i++) {
            deck.add(new DeathRay(gameRoom));
        }
        for (int i = 0; i < 6; i++) {
            deck.add(new FireBall(gameRoom));
        }
        for (int i = 0; i < 6; i++) {
            deck.add(new PoisonArrow(gameRoom));
        }
        for (int i = 0; i < 6; i++) {
            deck.add(new ManaSiphon(gameRoom));
        }
        Collections.shuffle(deck);
        gameRoom.setDeck(deck);
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < userInLobby.size(); i++) {
            Player player = new Player(userInLobby.get(i),gameRoom);
            player.setTurnOrder(i+1);
            player.setTeam((i==0)||(i==2));
            playerList.add(player);
        }
        gameRoom.setPlayerList(playerList);
        return gameRepository.save(gameRoom);
    }
}
