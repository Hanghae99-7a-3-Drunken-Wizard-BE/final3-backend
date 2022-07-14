package com.example.game.Game.gameDataDto;

import com.example.game.Game.Game;
import com.example.game.Game.card.Card;
import com.example.game.Game.gameDataDto.response.*;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.CardRepository;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.PlayerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DtoGenerator {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final CardRepository cardRepository;

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

    public CardDrawResponseDto cardDrawResponseDtoMaker(Player player, List<Card> cardList) throws JsonProcessingException {
        List<Card> cardsOnHand = cardRepository.findByLyingPlace(player.getPlayerId());
        return new CardDrawResponseDto(cardsOnHand, cardList);
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
    public PoisonDamageCheckResponseDto poisonDamageCheckResponseDtoMaker (Player player, boolean gameOver) throws JsonProcessingException {
        PoisonDamageCheckResponseDto responseDto = new PoisonDamageCheckResponseDto(gameOver);
        responseDto.setPlayer(playerDtoMaker(player));
        return responseDto
;
    }
}
