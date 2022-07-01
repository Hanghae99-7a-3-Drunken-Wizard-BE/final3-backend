package com.example.game.Game;

import com.example.game.Game.card.ApplyCardToCharacter;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Deck;
import com.example.game.Game.card.magic.attack.BoulderStrike;
import com.example.game.Game.card.magic.curse.WeaknessExposure;
import com.example.game.Game.card.magic.enchantment.MagicAmplification;
import com.example.game.Game.card.magic.enchantment.MagicArmor;
import com.example.game.Game.card.magic.enchantment.Shield;
import com.example.game.Game.gameDataDto.response.GameStarterResponseDto;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.CardRepository;
import com.example.game.Game.repository.DeckRepository;
import com.example.game.Game.repository.PlayerRepository;
import com.example.game.Game.service.GameCloser;
import com.example.game.Game.service.GameStarter;
import com.example.game.Game.turn.PreTurn;
import com.example.game.model.User;
import com.example.game.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class testRunner implements ApplicationRunner {

    private final GameStarter gameStarter;
    private final GameCloser gameCloser;
    private final UserRepository userRepository;
    private final ApplyCardToCharacter applyCardToCharacter;
    private final PlayerRepository playerRepository;
    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;
    private final PreTurn preTurn;


    @Override
    public void run(ApplicationArguments args) throws Exception {


        User user1 = new User("user1", "111", "nickname1", "email@emal1.com");
        User user2 = new User("user2", "111", "nickname2", "email@emal2.com");
        User user3 = new User("user3", "111", "nickname3", "email@emal3.com");
        User user4 = new User("user4", "111", "nickname4", "email@emal4.com");

        List<User> userList = new ArrayList<>();

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        userRepository.saveAll(userList);

        Deck deck = new Deck();
        List<Card> allCards = new ArrayList<>();
        allCards.add(new MagicAmplification(deck));
        allCards.add(new WeaknessExposure(deck));
        allCards.add(new MagicArmor(deck));
        allCards.add(new Shield(deck));
        allCards.add(new Shield(deck));
        allCards.add(new BoulderStrike(deck));
        allCards.add(new BoulderStrike(deck));
        deck.setGameDeck(allCards);

        deckRepository.save(deck);

        GameRoom gameRoom = gameStarter.createGameRoom(userList);
        GameStarterResponseDto gameStarterResponseDto = new GameStarterResponseDto(gameRoom);
        ObjectWriter ow = new ObjectMapper().writer();
        String dtoToString = ow.writeValueAsString(gameStarterResponseDto);
        System.out.println(dtoToString);

        Player player = playerRepository.findById(1L).orElseThrow(()-> new NullPointerException("플레이어 없음"));

        List<Card> cards = new ArrayList<>();
        Card card1 = cardRepository.findByCardId(1L);
        Card card2 = cardRepository.findByCardId(2L);
        Card card3 = cardRepository.findByCardId(3L);
        Card card4 = cardRepository.findByCardId(4L);
        Card card5 = cardRepository.findByCardId(5L);
        Card card6 = cardRepository.findByCardId(6L);
        Card card7 = cardRepository.findByCardId(7L);
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);
        cards.add(card7);
        player.setCardsOnHand(cards);
        playerRepository.save(player);

        applyCardToCharacter.cardInitiator(1L, 2L, 4L);
        applyCardToCharacter.cardInitiator(1L, 3L, 5L);
        applyCardToCharacter.cardInitiator(1L, 3L, 6L);
        applyCardToCharacter.cardInitiator(1L, 4L, 7L);





    }
}
