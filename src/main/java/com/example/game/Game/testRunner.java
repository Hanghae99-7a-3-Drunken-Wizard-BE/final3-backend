package com.example.game.Game;

import com.example.game.Game.card.ApplyCardToCharacter;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.gameDataDto.ObjectBuilder;
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
import com.example.game.websocket.GameRoom;
import com.example.game.websocket.GameRoomRepository;
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

        User user1 = new User("user1", passwordEncoder.encode("qlalfqjsgh1!"), "nickname1", "email@emal1.com");
        User user2 = new User("user2", passwordEncoder.encode("qlalfqjsgh1!"), "nickname2", "email@emal2.com");
        User user3 = new User("user3", passwordEncoder.encode("qlalfqjsgh1!"), "nickname3", "email@emal3.com");
        User user4 = new User("user4", passwordEncoder.encode("qlalfqjsgh1!"), "nickname4", "email@emal4.com");
//        User user5 = new User("user5", "111", "nickname5", "email@emal5.com");
//        User user6 = new User("user6", "111", "nickname6", "email@emal6.com");
//        User user7 = new User("user7", "111", "nickname7", "email@emal7.com");
//        User user8 = new User("user8", "111", "nickname8", "email@emal8.com");
//

//
        user1.setRoomId("1");
        user2.setRoomId("1");
        user3.setRoomId("1");
        user4.setRoomId("1");
        List<User> userList = new ArrayList<>();
//        List<User> userList1 = new ArrayList<>();
//
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
//        userList1.add(user5);
//        userList1.add(user6);
//        userList1.add(user7);
//        userList1.add(user8);
//
        userRepository.saveAll(userList);
//        userRepository.saveAll(userList1);
//
        GameRoom gameRoom = new GameRoom("1", "testRoom");
        gameRoomRepository.save(gameRoom);
//
//        gameStarter.createGameRoom("1");
//        Card cm = cardRepository.findByCardName("Channeling Mana").get(0);
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
//
//        UseCardDto useCardDto = new UseCardDto();
//                useCardDto.setPlayerId(1L);
//                useCardDto.setTargetPlayerID(3L);
//                useCardDto.setCardId(1L);
//        Card card = cardRepository.getByCardId(1L);
//        System.out.println(card.getCardName());
//        System.out.println(actionTurn.cardMoveProcess(useCardDto));
//
//        gameCloser.closeGameRoom("1");
//        Game game3 = gameStarter.createGameRoom("3", userList);
//
//        System.out.println(endTurn.EndTrunCheck(playerRequestDto));











    }
}

