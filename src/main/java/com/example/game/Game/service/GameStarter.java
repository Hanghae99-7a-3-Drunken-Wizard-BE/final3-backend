package com.example.game.Game.service;

import com.example.game.Game.h2Package.Game;
import com.example.game.Game.h2Package.Card;
import com.example.game.Game.card.item.BeerMug;
import com.example.game.Game.card.item.LeftoverOctopus;
import com.example.game.Game.card.item.ManaPotion;
import com.example.game.Game.card.item.Panacea;
import com.example.game.Game.card.magic.attack.*;
import com.example.game.Game.card.magic.curse.*;
import com.example.game.Game.card.magic.enchantment.*;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.h2Package.Player;
import com.example.game.Game.repository.CardRepository;
import com.example.game.Game.repository.GameRepository;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.example.game.Game.repository.GameRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
@RequiredArgsConstructor
public class GameStarter {
private final GameRoomRepository gameRoomRepository;
    private final GameRepository gameRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createGameRoom (String roomId){
        List<User> userInLobby = userRepository.findByRoomId(roomId);
        if(userInLobby.size() != 4) {return;}
        Game game = new Game(roomId);
        List<Card> gameDeck = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new BoulderStrike(game));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new DeathRay(game));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new FireBall(game));
        }
        for (int i = 0; i < 10; i++) {
            gameDeck.add(new MagicMissile(game));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new ManaSiphon(game));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new PoisonArrow(game));
        }
        for (int i = 0; i < 2; i++) {
            gameDeck.add(new ChannelingMana(game));
        }
        for (int i = 0; i < 6; i++) {
            gameDeck.add(new Heal(game));
        }
        for (int i = 0; i < 2; i++) {
            gameDeck.add(new PartyHeal(game));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new Resistance(game));
        }
        for (int i = 0; i < 6; i++) {
            gameDeck.add(new Shield(game));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new Dispel(game));
        }
        for (int i = 0; i < 2; i++) {
            gameDeck.add(new MagicAmplification(game));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new MagicArmor(game));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new Mute(game));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new Petrification(game));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new WeaknessExposure(game));
        }
        for (int i = 0; i < 6; i++) {
            gameDeck.add(new Venom(game));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new Yfeputs(game));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new Sleep(game));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new MagicAttenuation(game));
        }
        for (int i = 0; i < 9; i++) {
            gameDeck.add(new BeerMug(game));
        }
        for (int i = 0; i < 7; i++) {
            gameDeck.add(new LeftoverOctopus(game));
        }
        for (int i = 0; i < 7; i++) {
            gameDeck.add(new ManaPotion(game));
        }
        for (int i = 0; i < 7; i++) {
            gameDeck.add(new Panacea(game));
        }

        game.setDeck(gameDeck);
        List<Boolean> preEmptive1 = Arrays.asList(true, false);
        List<Boolean> preEmptive2 = Arrays.asList(true, false);
        List<Integer> order = new ArrayList<>();
        List<Integer> orderFirst1 = Arrays.asList(1,2);
        List<Integer> orderFirst2 = Arrays.asList(2,1);
        List<Integer> orderSecond1 = Arrays.asList(3,4);
        List<Integer> orderSecond2 = Arrays.asList(4,3);
        List<CharactorClass> classes =
                Arrays.asList(
                        CharactorClass.INVOKER,
                        CharactorClass.ENCHANTER,
                        CharactorClass.WAROCK,
                        CharactorClass.HEALER,
                        CharactorClass.FARSEER,
                        CharactorClass.BLOODMAGE);
        Collections.shuffle(classes);
        Collections.shuffle(preEmptive1);
        Collections.shuffle(preEmptive2);
        if (preEmptive1.get(0)) {
            if(preEmptive2.get(0)) {
                order.addAll(orderFirst1);order.addAll(orderSecond1);
            } else {order.addAll(orderFirst2);order.addAll(orderSecond2);}
        } else {
            if(preEmptive2.get(0)) {
                order.addAll(orderSecond1);order.addAll(orderFirst1);
            } else {order.addAll(orderSecond2);order.addAll(orderFirst2);}
        }
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < userInLobby.size(); i++) {
            Player player = new Player(userInLobby.get(i), game);
            player.setTeam(i%2 == 0);
            player.setTurnOrder(order.get(i));
            player.setCharactorClass(classes.get(i));
            playerList.add(player);
        }
        game.setPlayerList(playerList);
        gameRepository.save(game);
        List<Card> deck = cardRepository.findByGame(game);
        Collections.shuffle(deck);
        for (int i = 0; i < deck.size(); i++) {
            deck.get(i).setCardOrder(i);
        }
    }
}
