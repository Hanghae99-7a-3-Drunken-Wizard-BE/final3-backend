package com.example.game.Game;

import com.example.game.Game.card.ApplyCardToCharacter;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.gameDataDto.ObjectBuilder;
import com.example.game.Game.h2Package.GameRoom;
import com.example.game.Game.repository.CardRepository;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.PlayerRepository;
import com.example.game.Game.service.GameStarter;
import com.example.game.Game.turn.ActionTurn;
import com.example.game.Game.turn.EndTurn;
import com.example.game.Game.turn.PreTurn;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.example.game.websocket.GameController;


import com.example.game.Game.repository.GameRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class testRunner implements ApplicationRunner {

    private final GameStarter gameStarter;
    private final ObjectBuilder objectBuilder;
    private final JsonStringBuilder jsonStringBuilder;
    private final UserRepository userRepository;
    private final ApplyCardToCharacter applyCardToCharacter;
    private final PlayerRepository playerRepository;
    private final CardRepository cardRepository;
    private final GameRepository gameRepository;
    private final GameController gameController;
    private final GameRoomRepository gameRoomRepository;
    private final PreTurn preTurn;
    private final ActionTurn actionTurn;
    private final EndTurn endTurn;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
//        User user5 = new User("user5", "111", "nickname5", "email@emal5.com");
//        User user6 = new User("user6", "111", "nickname6", "email@emal6.com");
//        User user7 = new User("user7", "111", "nickname7", "email@emal7.com");
//        User user8 = new User("user8", "111", "nickname8", "email@emal8.com");
//

//        List<User> userList = new ArrayList<>();
//        List<User> userList1 = new ArrayList<>();
//
//        userList.add(userRepository.findByUsername("aksdnjs88").orElse(null));
//        userList.add(userRepository.findById(8L).orElse(null));
//        userList.add(userRepository.findById(9L).orElse(null));
//        userList.add(userRepository.findById(10L).orElse(null));
//        userList1.add(user5);
//        userList1.add(user6);
//        userList1.add(user7);
//        userList1.add(user8);
//
//        userRepository.saveAll(userList);
//        userRepository.saveAll(userList1);
////
//        GameRoom gameRoom = new GameRoom("1", "testRoom");
////
//        gameRoom.setPlayer1(userRepository.findByUsername("aksdnjs88").orElse(null).getId());
//        gameRoom.setPlayer2(8L);
//        gameRoom.setPlayer3(9L);
//        gameRoom.setPlayer4(10L);
//        gameRoomRepository.save(gameRoom);
//////
//        gameStarter.createGameRoom("1");
//        Card cm = cardRepository.findByCardName("Panacea").get(0);
//
//        UseCardDto useCardDto = new UseCardDto();
//        useCardDto.setCardId(cm.getCardId());
//        actionTurn.cardMoveProcess(1L,useCardDto);
//
//        Game game = gameRepository.findByRoomId("1");
//        List<Card> deck = cardRepository.findByLyingPlaceAndGameOrderByCardOrderAsc(0,game);
//
//        System.out.println(deck.get(0).getCardName());
//        System.out.println(deck.get(1).getCardName());
//        System.out.println(deck.get(2).getCardName());
//        System.out.println(deck.get(3).getCardName());



//        Game game = gameStarter.createGameRoom("1");
//        gameRepository.save(game);
//
//        List<Card> cards = game1.getDeck();
//        System.out.println(jsonStringBuilder.cardDataToJson(cards));
//        Game game2 = gameStarter.createGameRoom("2", userList1);
//
//        System.out.println(jsonStringBuilder.gameStarter(game1));
//
//        PlayerRequestDto playerRequestDto = PlayerRequestDto.builder()
//                .playerId(1L)
//                .build();
//
//        String cardDrawResponse = preTurn.preturnStartCheck(playerRequestDto);
//        System.out.println(cardDrawResponse);
//
//        CardRequestDto cardRequestDto1 = new CardRequestDto();
//
//        CardRequestDto cardRequestDto2 = new CardRequestDto();
//        cardRequestDto1.setCardId(1L);
//        cardRequestDto2.setCardId(2L);
//
//        List<CardRequestDto> cards = new ArrayList<>();
//        cards.add(cardRequestDto1);
//        cards.add(cardRequestDto2);
//
//        CardSelectRequestDto cardSelectRequestDto = new CardSelectRequestDto();
//        cardSelectRequestDto.setPlayerId(1L);
//        cardSelectRequestDto.setSelectedCards(cards);
//
//        System.out.println(preTurn.cardDrawResponse(cardSelectRequestDto));
//        Player player = playerRepository.findById(1L).orElseThrow(()->new RuntimeException("You Are Fucked Up"));
//        player.setMana(9999);
//        playerRepository.save(player);
//        List<Card> deck = cardRepository.findAll();
//        for (Card card : deck) {
//            UseCardDto useCardDto = new UseCardDto();
//            useCardDto.setTargetPlayerId(3L);
//            useCardDto.setCardId(card.getCardId());
//            System.out.println(card.getCardName());
//            System.out.println(actionTurn.cardMoveProcess(1L, useCardDto));
//        }
//        gameCloser.closeGameRoom("1");
//        Game game3 = gameStarter.createGameRoom("3", userList);
//
//        System.out.println(endTurn.EndTrunCheck(playerRequestDto));











    }
}

