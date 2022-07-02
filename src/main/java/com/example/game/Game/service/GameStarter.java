package com.example.game.Game.service;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.item.BeerMug;
import com.example.game.Game.card.item.LeftoverOctopus;
import com.example.game.Game.card.item.ManaPotion;
import com.example.game.Game.card.item.Panacea;
import com.example.game.Game.card.magic.attack.*;
import com.example.game.Game.card.magic.curse.*;
import com.example.game.Game.card.magic.enchantment.*;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.player.Player;
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

    @Transactional
    public GameRoom createGameRoom (List<User> userInLobby){
        GameRoom gameRoom = new GameRoom();
        List<Card> gameDeck = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            gameDeck.add(new BoulderStrike(gameRoom));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new DeathRay(gameRoom));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new FireBall(gameRoom));
        }
        for (int i = 0; i < 10; i++) {
            gameDeck.add(new MagicMissile(gameRoom));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new ManaSiphon(gameRoom));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new PoisonArrow(gameRoom));
        }
        for (int i = 0; i < 2; i++) {
            gameDeck.add(new ChannelingMana(gameRoom));
        }
        for (int i = 0; i < 6; i++) {
            gameDeck.add(new Heal(gameRoom));
        }
        for (int i = 0; i < 2; i++) {
            gameDeck.add(new PartyHeal(gameRoom));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new Resistance(gameRoom));
        }
        for (int i = 0; i < 6; i++) {
            gameDeck.add(new Shield(gameRoom));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new Dispel(gameRoom));
        }
        for (int i = 0; i < 2; i++) {
            gameDeck.add(new MagicAmplification(gameRoom));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new MagicArmor(gameRoom));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new Mute(gameRoom));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new Petrification(gameRoom));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new WeaknessExposure(gameRoom));
        }
        for (int i = 0; i < 6; i++) {
            gameDeck.add(new Venom(gameRoom));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new Yfeputs(gameRoom));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new Sleep(gameRoom));
        }
        for (int i = 0; i < 4; i++) {
            gameDeck.add(new MagicAttenuation(gameRoom));
        }
        for (int i = 0; i < 9; i++) {
            gameDeck.add(new BeerMug(gameRoom));
        }
        for (int i = 0; i < 7; i++) {
            gameDeck.add(new LeftoverOctopus(gameRoom));
        }
        for (int i = 0; i < 7; i++) {
            gameDeck.add(new ManaPotion(gameRoom));
        }
        for (int i = 0; i < 7; i++) {
            gameDeck.add(new Panacea(gameRoom));
        }

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
