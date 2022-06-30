package com.example.game.Game.service;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Deck;
import com.example.game.Game.card.magic.attack.*;
import com.example.game.Game.card.magic.curse.WeaknessExposure;
import com.example.game.Game.card.magic.enchantment.MagicAmplification;
import com.example.game.Game.card.magic.enchantment.MagicArmor;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.DeckRepository;
import com.example.game.Game.repository.GameRepository;
import com.example.game.model.User;
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
        List<CharactorClass> classes =
                Arrays.asList(
                        CharactorClass.INVOKER,
                        CharactorClass.ENCHANTER,
                        CharactorClass.WAROCK,
                        CharactorClass.HEALER,
                        CharactorClass.FARSEER,
                        CharactorClass.BLOODMAGE);
        Collections.shuffle(classes);
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < userInLobby.size(); i++) {
            Player player = new Player(userInLobby.get(i),gameRoom);
            player.setTurnOrder(i+1);
            player.setTeam((i==0)||(i==2));
            player.setCharactorClass(classes.get(i));
            playerList.add(player);
        }
        gameRoom.setPlayerList(playerList);
        return gameRepository.save(gameRoom);
    }
}
