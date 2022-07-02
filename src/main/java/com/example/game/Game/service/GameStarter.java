package com.example.game.Game.service;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.DeckRepository;
import com.example.game.Game.repository.GameRepository;
import com.example.game.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
@RequiredArgsConstructor
public class GameStarter {

    private final GameRepository gameRepository;
    private final DeckRepository deckRepository;

    @Transactional
    public GameRoom createGameRoom (List<User> userInLobby){
        GameRoom gameRoom = new GameRoom();
        List<Card> gameCards = deckRepository.getById(1L).getGameDeck();
        List<Card> gameDeck = new ArrayList<>(gameCards);
        Collections.shuffle(gameDeck);
        gameRoom.setDeck(gameDeck);
        List<Boolean> preEmptive = Arrays.asList(true, false);
        List<Integer> order = new ArrayList<>();
        List<Integer> orderFirst = Arrays.asList(1,3);
        List<Integer> orderSecond = Arrays.asList(2,4);
        List<CharactorClass> classes =
                Arrays.asList(
                        CharactorClass.INVOKER,
                        CharactorClass.ENCHANTER,
                        CharactorClass.WAROCK,
                        CharactorClass.HEALER,
                        CharactorClass.FARSEER,
                        CharactorClass.BLOODMAGE);
        Collections.shuffle(classes);
        Collections.shuffle(orderFirst);
        Collections.shuffle(orderSecond);
        Collections.shuffle(preEmptive);
        if (preEmptive.get(0)) {order.addAll(orderFirst);order.addAll(orderSecond);}
        else{order.addAll(orderSecond);order.addAll(orderFirst);}
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < userInLobby.size(); i++) {
            Player player = new Player(userInLobby.get(i),gameRoom);
            player.setTeam(i<2);
            player.setTurnOrder(order.get(i));
            player.setCharactorClass(classes.get(i));
            playerList.add(player);
        }
        gameRoom.setPlayerList(playerList);
        return gameRepository.save(gameRoom);
    }
}
