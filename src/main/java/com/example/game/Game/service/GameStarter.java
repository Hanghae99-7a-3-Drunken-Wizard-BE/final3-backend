package com.example.game.Game.service;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.magic.attack.*;
import com.example.game.Game.card.magic.curse.WeaknessExposure;
import com.example.game.Game.card.magic.enchantment.MagicAmplification;
import com.example.game.Game.card.magic.enchantment.MagicArmor;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.player.Player;
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

    @Transactional
    public GameRoom createGameRoom (List<User> userInLobby){
        GameRoom gameRoom = new GameRoom();
        LinkedList<Card> deck = new LinkedList<>();

//        for (int i = 0; i < 1; i++) {
//            deck.add(new Heal(gameRoom));
//        }
        deck.add(new MagicAmplification(gameRoom));
        deck.add(new WeaknessExposure(gameRoom));
        deck.add(new MagicArmor(gameRoom));
        deck.add(new BoulderStrike(gameRoom));

        Collections.shuffle(deck);
        gameRoom.setDeck(deck);
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
