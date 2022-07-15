package com.example.game.Game.turn;

import com.example.game.Game.Game;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.CardType;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.gameDataDto.request.CardRequestDto;
import com.example.game.Game.gameDataDto.request.CardSelectRequestDto;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.CardRepository;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.PlayerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PreTurn {

    private final PlayerRepository playerRepository;
    private final CardRepository cardRepository;
    private final JsonStringBuilder jsonStringBuilder;
    private final GameRepository gameRepository;


    @Transactional
    public String preturnStartCheck(Long playerId) throws JsonProcessingException {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new NullPointerException("해당 플레이어가 존재하지 않습니다"));
        player.applyPoison();
        List<Player> playerTeam = playerRepository.findByGameAndTeam(player.getGame(), player.isTeam());
        boolean gameOver = (playerTeam.get(0).isDead() && playerTeam.get(1).isDead());
        return jsonStringBuilder.poisonDamageCheckResponseDtoJsonBuilder(player, gameOver);
    }


    @Transactional
    public String cardDrawInitiator(Long playerId) throws JsonProcessingException {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new NullPointerException("해당 플레이어가 존재하지 않습니다"));
        Game game = player.getGame();
        List<Card> deck = cardRepository.findByLyingPlaceAndGameOrderByCardOrderAsc(0,game);
        List<Card> cardOnHand = cardRepository.findByLyingPlace(playerId);
        if (deck.size() < 3) {shuffleGraveyardToDeck(game);}
        if (cardOnHand.size() < 6) {
            int selectable = Math.min(6 - cardOnHand.size(), 2);
            return jsonStringBuilder.cardDrawResponseDtoJsonBuilder(selectable);
        } else {
            return jsonStringBuilder.noMoreDrawResponseDtoJsonBuilder(cardOnHand);
        }
    }

    @Transactional
    public String cardDrawResponse(Long playerId,CardSelectRequestDto requestDto) throws JsonProcessingException {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new NullPointerException("해당 플레이어가 존재하지 않습니다"));
        Game game = gameRepository.findByRoomId(player.getGame().getRoomId());
        List<Card> cardsOnHand = cardRepository.findByLyingPlaceAndGame(playerId, game);

        if (requestDto.getSelectedCards() == null) {
        } else if (requestDto.getSelectedCards().size() == 1 && player.getCharactorClass() != CharactorClass.FARSEER) {
            Card card = cardRepository.findByCardId(requestDto.getSelectedCards().get(0).getCardId());
            player.addOnHand(card);
        } else if(requestDto.getSelectedCards().size() == 2){
            List<CardRequestDto> selectedCards = requestDto.getSelectedCards();
            for (CardRequestDto selectedCard : selectedCards) {
                Card card = cardRepository.findByCardId(selectedCard.getCardId());
                player.addOnHand(card);
            }
        }
        boolean drawSuccess;
        Card additionalCard = cardRepository.findByLyingPlaceAndGameOrderByCardOrderAsc(0,game).get(0);
        if(cardsOnHand.size() >= 6) {
            List<Card> cardList = cardRepository.findByLyingPlace(player.getPlayerId());
            return jsonStringBuilder.additionalDrawResponseDtoJsonBuilder(cardList, false);}
        else{
            if (
                    player.getCharactorClass().equals(CharactorClass.INVOKER)||
                    player.getCharactorClass().equals(CharactorClass.ENCHANTER)||
                    player.getCharactorClass().equals(CharactorClass.WAROCK)) {
                if (player.getCharactorClass().equals(CharactorClass.INVOKER)
                        && additionalCard.getCardType().equals(CardType.ATTACK)){
                    player.addOnHand(additionalCard);
                    drawSuccess = true;}
                else if (player.getCharactorClass().equals(CharactorClass.ENCHANTER)
                        && additionalCard.getCardType().equals(CardType.ENCHANTMENT)){
                    player.addOnHand(additionalCard);
                    drawSuccess = true;}
                else if (player.getCharactorClass().equals(CharactorClass.WAROCK)
                        && additionalCard.getCardType().equals(CardType.CURSE)){
                    player.addOnHand(additionalCard);
                    drawSuccess = true;}
                else {drawSuccess = false;
                game.addTograveyard(additionalCard);
                }
                List<Card> cardList = cardRepository.findByLyingPlace(player.getPlayerId());
                return jsonStringBuilder.additionalDrawResponseDtoJsonBuilder(cardList, drawSuccess);
            } else {
                List<Card> cardList = cardRepository.findByLyingPlace(player.getPlayerId());
                return jsonStringBuilder.noMoreDrawResponseDtoJsonBuilder(cardList);
            }}
    }

    public String actionTurnCheck(Long playerId) throws JsonProcessingException {
        Player player = playerRepository.findById(playerId).orElseThrow(
                ()->new NullPointerException("해당 유저가 존재하지 않습니다"));
        return jsonStringBuilder.preTurnCheckResponseDtoJsonBuilder(player);
    }

    private void shuffleGraveyardToDeck(Game game) {
        List<Card> graveyard = cardRepository.findByLyingPlaceAndGame(-1L, game);
        game.graveyardToDeck(graveyard);
        List<Card> deck = cardRepository.findByGame(game);
        Collections.shuffle(deck);
        for (int i = 0; i < deck.size(); i++) {
            deck.get(i).setCardOrder(i);
        }
        cardRepository.saveAll(deck);
    }
}

