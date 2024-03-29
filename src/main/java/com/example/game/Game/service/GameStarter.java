package com.example.game.Game.service;


import com.example.game.Game.card.item.BeerMug;
import com.example.game.Game.card.item.LeftoverOctopus;
import com.example.game.Game.card.item.ManaPotion;
import com.example.game.Game.card.item.Panacea;
import com.example.game.Game.card.magic.attack.*;
import com.example.game.Game.card.magic.curse.*;
import com.example.game.Game.card.magic.enchantment.*;
import com.example.game.Game.h2Package.Card;
import com.example.game.Game.h2Package.Game;
import com.example.game.Game.h2Package.GameRoom;
import com.example.game.Game.h2Package.Player;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.repository.CardRepository;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.GameRoomRepository;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GameStarter {
private final GameRoomRepository gameRoomRepository;
    private final GameRepository gameRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Transactional("gameTransactionManager")
    public void createGameRoom (String roomId){
        GameRoom gameRoom = gameRoomRepository.findByRoomId(roomId);
        System.out.println(gameRoom.getPlayer1() + " 플레이어 1확인");
        System.out.println(gameRoom.getPlayer2() + " 플레이어 1확인");
        System.out.println(gameRoom.getPlayer3() + " 플레이어 1확인");
        System.out.println(gameRoom.getPlayer4() + " 플레이어 1확인");
        List<User> userInLobby = new ArrayList<>();
        if(gameRoom.getPlayer1() != null && gameRoom.getPlayer1() > 0) {
            userInLobby.add(userRepository.findById(gameRoom.getPlayer1()).orElse(null));
        }
        if(gameRoom.getPlayer2() != null && gameRoom.getPlayer2() > 0) {
            userInLobby.add(userRepository.findById(gameRoom.getPlayer2()).orElse(null));
        }
        if(gameRoom.getPlayer3() != null && gameRoom.getPlayer3() > 0) {
            userInLobby.add(userRepository.findById(gameRoom.getPlayer3()).orElse(null));
        }
        if(gameRoom.getPlayer4() != null && gameRoom.getPlayer4() > 0) {
            userInLobby.add(userRepository.findById(gameRoom.getPlayer4()).orElse(null));
        }
        if(userInLobby.size() != 4) {return;}
        Game game = new Game(roomId, gameRoom.getRoomName());
        List<Card> gameDeck = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            gameDeck.add(new BoulderStrike(game));
        }
        for (int i = 0; i < 6; i++) {
            gameDeck.add(new DeathRay(game));
        }
        for (int i = 0; i < 6; i++) {
            gameDeck.add(new FireBall(game));
        }
        for (int i = 0; i < 15; i++) {
            gameDeck.add(new MagicMissile(game));
        }
        for (int i = 0; i < 6; i++) {
            gameDeck.add(new ManaSiphon(game));
        }
        for (int i = 0; i < 6; i++) {
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

        List<Boolean> oddEven = Arrays.asList(true, false);
        List<Integer> odd = Arrays.asList(1, 3);
        List<Integer> even = Arrays.asList(2, 4);
        List<Integer> order = new ArrayList<>();
        List<CharactorClass> classes =
                Arrays.asList(
                        CharactorClass.INVOKER,
                        CharactorClass.ENCHANTER,
                        CharactorClass.WAROCK,
                        CharactorClass.HEALER,
                        CharactorClass.FARSEER,
                        CharactorClass.BLOODMAGE);
        Collections.shuffle(classes);
        Collections.shuffle(oddEven);
        Collections.shuffle(odd);
        Collections.shuffle(even);
        if (oddEven.get(0)) {
            order.add(odd.get(0));
            order.add(even.get(0));
            order.add(odd.get(1));
            order.add(even.get(1));
        } else {
            order.add(even.get(0));
            order.add(odd.get(0));
            order.add(even.get(1));
            order.add(odd.get(1));
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
