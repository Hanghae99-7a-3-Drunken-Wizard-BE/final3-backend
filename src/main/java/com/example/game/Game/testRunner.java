package com.example.game.Game;

import com.example.game.Game.card.ApplyCardToCharacter;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Deck;
import com.example.game.Game.card.item.BeerMug;
import com.example.game.Game.card.item.LeftoverOctopus;
import com.example.game.Game.card.item.ManaPotion;
import com.example.game.Game.card.item.Panacea;
import com.example.game.Game.card.magic.attack.*;
import com.example.game.Game.card.magic.curse.*;
import com.example.game.Game.card.magic.enchantment.*;
import com.example.game.Game.gameDataDto.ObjectBuilder;
import com.example.game.Game.gameDataDto.request.CardRequestDto;
import com.example.game.Game.gameDataDto.request.CardSelectRequestDto;
import com.example.game.Game.gameDataDto.request.PlayerRequestDto;
import com.example.game.Game.gameDataDto.request.UseCardDto;
import com.example.game.Game.gameDataDto.response.CardUseResponseDto;
import com.example.game.Game.gameDataDto.response.CardsDto;
import com.example.game.Game.gameDataDto.response.GameStarterResponseDto;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.CardRepository;
import com.example.game.Game.repository.DeckRepository;
import com.example.game.Game.repository.PlayerRepository;
import com.example.game.Game.service.GameCloser;
import com.example.game.Game.service.GameStarter;
import com.example.game.Game.turn.ActionTurn;
import com.example.game.Game.turn.EndTurn;
import com.example.game.Game.turn.PreTurn;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class testRunner implements ApplicationRunner {

    private final GameStarter gameStarter;
    private final GameCloser gameCloser;
    private final ObjectBuilder objectBuilder;
    private final UserRepository userRepository;
    private final ApplyCardToCharacter applyCardToCharacter;
    private final PlayerRepository playerRepository;
    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;
    private final PreTurn preTurn;
    private final ActionTurn actionTurn;
    private final EndTurn endTurn;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Deck deck = new Deck();
        List<Card> allCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            allCards.add(new BoulderStrike(deck));
        }
        for (int i = 0; i < 4; i++) {
            allCards.add(new DeathRay(deck));
        }
        for (int i = 0; i < 4; i++) {
            allCards.add(new FireBall(deck));
        }
        for (int i = 0; i < 10; i++) {
            allCards.add(new MagicMissile(deck));
        }
        for (int i = 0; i < 4; i++) {
            allCards.add(new ManaSiphon(deck));
        }
        for (int i = 0; i < 4; i++) {
            allCards.add(new PoisonArrow(deck));
        }
        for (int i = 0; i < 2; i++) {
            allCards.add(new ChannelingMana(deck));
        }
        for (int i = 0; i < 6; i++) {
            allCards.add(new Heal(deck));
        }
        for (int i = 0; i < 2; i++) {
            allCards.add(new PartyHeal(deck));
        }
        for (int i = 0; i < 4; i++) {
            allCards.add(new Resistance(deck));
        }
        for (int i = 0; i < 6; i++) {
            allCards.add(new Shield(deck));
        }
        for (int i = 0; i < 4; i++) {
            allCards.add(new Dispel(deck));
        }
        for (int i = 0; i < 2; i++) {
            allCards.add(new MagicAmplification(deck));
        }
        for (int i = 0; i < 4; i++) {
            allCards.add(new MagicArmor(deck));
        }
        for (int i = 0; i < 4; i++) {
            allCards.add(new Mute(deck));
        }
        for (int i = 0; i < 4; i++) {
            allCards.add(new Petrification(deck));
        }
        for (int i = 0; i < 4; i++) {
            allCards.add(new WeaknessExposure(deck));
        }
        for (int i = 0; i < 6; i++) {
            allCards.add(new Venom(deck));
        }
        for (int i = 0; i < 4; i++) {
            allCards.add(new Yfeputs(deck));
        }
        for (int i = 0; i < 4; i++) {
            allCards.add(new Sleep(deck));
        }
        for (int i = 0; i < 4; i++) {
            allCards.add(new MagicAttenuation(deck));
        }
        for (int i = 0; i < 9; i++) {
            allCards.add(new BeerMug(deck));
        }
        for (int i = 0; i < 7; i++) {
            allCards.add(new LeftoverOctopus(deck));
        }
        for (int i = 0; i < 7; i++) {
            allCards.add(new ManaPotion(deck));
        }
        for (int i = 0; i < 7; i++) {
            allCards.add(new Panacea(deck));
        }
        deck.setGameDeck(allCards);
        deckRepository.save(deck);

//
//        User user1 = new User("user1", "111", "nickname1", "email@emal1.com");
//        User user2 = new User("user2", "111", "nickname2", "email@emal2.com");
//        User user3 = new User("user3", "111", "nickname3", "email@emal3.com");
//        User user4 = new User("user4", "111", "nickname4", "email@emal4.com");
//
//        List<User> userList = new ArrayList<>();
//
//        userList.add(user1);
//        userList.add(user2);
//        userList.add(user3);
//        userList.add(user4);
//
//        userRepository.saveAll(userList);
//
//        GameRoom gameRoom = gameStarter.createGameRoom(userList);
//
//        PlayerRequestDto playerRequestDto = PlayerRequestDto.builder()
//                .playerId(1L)
//                .build();
//
//        String cardDrawResponse = preTurn.preturnStartCheck(playerRequestDto);
//        System.out.println(cardDrawResponse);
//
//        CardRequestDto cardRequestDto1 = CardRequestDto.builder()
//                .cardId()
//                .build();
//
//        CardRequestDto cardRequestDto2 = CardRequestDto.builder()
//                .cardId(2L)
//                .build();
//
//        List<CardRequestDto> cards = new ArrayList<>();
//        cards.add(cardRequestDto1);
//        cards.add(cardRequestDto2);
//
//        CardSelectRequestDto cardSelectRequestDto = CardSelectRequestDto.builder()
//                .playerId(1L)
//                .selectedCards(cards)
//                .build();
//
//        System.out.println(preTurn.cardDrawResponse(cardSelectRequestDto));
//
//        System.out.println(preTurn.actionTurnCheck(playerRequestDto));
//
//        UseCardDto useCardDto = UseCardDto.builder()
//                .playerId(1L)
//                .targetPlayerID(3L)
//                .cardId(1L)
//                .build();
//
//        System.out.println(actionTurn.cardMoveProcess(useCardDto));
//
//        System.out.println(endTurn.EndTrunCheck(playerRequestDto));











    }
}
