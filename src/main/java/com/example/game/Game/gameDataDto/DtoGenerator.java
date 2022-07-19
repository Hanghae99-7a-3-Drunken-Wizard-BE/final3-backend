package com.example.game.Game.gameDataDto;

import com.example.game.Game.Game;
import com.example.game.Game.card.Card;
import com.example.game.Game.gameDataDto.response.*;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.CardRepository;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.PlayerRepository;
import com.example.game.dto.response.GameRoomListResponseDto;
import com.example.game.dto.response.GameRoomResponseDto;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.example.game.websocket.GameRoom;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DtoGenerator {
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardUseResponseDto cardUseResponseDtoMaker(List<Player> players, boolean gameOver) throws JsonProcessingException {
        CardUseResponseDto cardUseResponseDto = new CardUseResponseDto();
        List<PlayerDto> playerDtos = new ArrayList<>();
        for (Player player : players) {
            playerDtos.add(playerDtoMaker(player));
        }
        cardUseResponseDto.setPlayers(playerDtos);
        cardUseResponseDto.setGameOver(gameOver);
        return cardUseResponseDto;
    }

    private PlayerDto playerDtoMaker(Player player) throws JsonProcessingException {
        List<Card> cards = cardRepository.findByLyingPlace(player.getPlayerId());
        return new PlayerDto(player, cards);
    }

    public CardDrawResponseDto cardDrawResponseDtoMaker(int selectable) throws JsonProcessingException {
        return new CardDrawResponseDto(selectable);
    }

    public EndTurnResponseDto EndTurnResponseDtoMaker(Player player, Player nextPlayer) throws JsonProcessingException {
        List<Card> cardsOnHand = cardRepository.findByLyingPlace(player.getPlayerId());
        return new EndTurnResponseDto(player,cardsOnHand, nextPlayer);
    }

    public GameStarterResponseDto gameStarterResponseDtoMaker (Game game) throws JsonProcessingException {
        List<Player> players = playerRepository.findByGame(game);
        List<PlayerDto> playerDtos = new ArrayList<>();
        for (Player player : players) {
            playerDtos.add(playerDtoMaker(player));
        }
        return new GameStarterResponseDto(playerDtos);
    }

    public PreTurnStartCheckResponseDto preTurnStartCheckResponseDtoMaker(Player player, boolean gameOver) throws JsonProcessingException {
        Game game = gameRepository.findByRoomId(player.getGame().getRoomId());
        List<Card> deck;
        if (cardRepository.findByLyingPlaceAndGameOrderByCardOrderAsc(0,game).size() < 3) {
            GraveyardToDeck(game);
            deck = shuffleDeck(game);
        } else {deck = cardRepository.findByLyingPlaceAndGameOrderByCardOrderAsc(0,game);}
        cardRepository.saveAll(cardRepository.findByGame(game));
        List<Card> cards = new ArrayList<>();
        if (player.getCharactorClass().equals(CharactorClass.FARSEER)) {
            for (int i = 0; i < 3; i++) {
                cards.add(deck.get(i));
            }
        } else {
            for (int i = 0; i < 2; i++) {
                cards.add(deck.get(i));
            }
        }
        PreTurnStartCheckResponseDto responseDto = new PreTurnStartCheckResponseDto(gameOver, cards);
        responseDto.setPlayer(playerDtoMaker(player));
        return responseDto;
    }

    public GameRoomListResponseDto gameRoomListResponseDtoMaker (Page<GameRoom> gameRoomList) throws JsonProcessingException {
        GameRoomListResponseDto listResponseDto = new GameRoomListResponseDto();
        List<GameRoomResponseDto> roomResponseDtos = new ArrayList<>();
        for (GameRoom gameRoom : gameRoomList) {
            roomResponseDtos.add(gameRoomResponseDtoMaker(gameRoom));
        }
        listResponseDto.setGameRoomList(roomResponseDtos);
        return listResponseDto;
    }

    public GameRoomResponseDto gameRoomResponseDtoMaker(GameRoom gameRoom) {
        User player1 = userRepository.findById((gameRoom.getPlayer1() > 0) ? gameRoom.getPlayer1() : gameRoom.getPlayer1() * -1).orElse(null);
        User player2 = userRepository.findById((gameRoom.getPlayer1() > 0) ? gameRoom.getPlayer2() : gameRoom.getPlayer2() * -1).orElse(null);
        User player3 = userRepository.findById((gameRoom.getPlayer1() > 0) ? gameRoom.getPlayer3() : gameRoom.getPlayer3() * -1).orElse(null);
        User player4 = userRepository.findById((gameRoom.getPlayer1() > 0) ? gameRoom.getPlayer4() : gameRoom.getPlayer4() * -1).orElse(null);
        return new GameRoomResponseDto(
                gameRoom.getRoomId(),
                gameRoom.getRoomName(),
                gameRoom.getPlayer1(),
                (player1 != null) ? player1.getNickname() : null,
                gameRoom.getPlayer2(),
                (player2 != null) ? player2.getNickname() : null,
                gameRoom.getPlayer3(),
                (player3 != null) ? player3.getNickname() : null,
                gameRoom.getPlayer4(),
                (player4 != null) ? player4.getNickname() : null
        );
    }

    public void GraveyardToDeck(Game game) {
        List<Card> graveyard = cardRepository.findByLyingPlaceAndGame(-1L, game);
        for(Card card : graveyard) {
            card.setLyingPlace(0L);
        }

    }

    public List<Card> shuffleDeck(Game game) {
        List<Card> deck = cardRepository.findByGame(game);
        Collections.shuffle(deck);
        for (int i = 0; i < deck.size(); i++) {
            deck.get(i).setCardOrder(i);
        } return deck;
    }

}
