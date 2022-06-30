package com.example.game.Game.turn;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.Card;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.gameDataDto.request.CardSelectRequestDto;
import com.example.game.Game.gameDataDto.request.PlayerRequestDto;
import com.example.game.Game.gameDataDto.subDataDto.CardsDto;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.CardRepository;
import com.example.game.Game.repository.PlayerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PreTurn {

    private final PlayerRepository playerRepository;
    private final CardRepository cardRepository;
    private final JsonStringBuilder jsonStringBuilder;



    @Transactional
    public String cardDrawIntiator(PlayerRequestDto requestDto) throws JsonProcessingException {
        Long playerId = requestDto.getPlayerId();
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new NullPointerException("해당 플레이어가 존재하지 않습니다"));
        GameRoom gameRoom = player.getGameRoom();
        List<Card> deck = gameRoom.getDeck().getCards();
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
        return jsonStringBuilder.cardDrawResponseDtoJsonBuilder(player, cards);
    }

    @Transactional
    public String cardDrawResponse(CardSelectRequestDto requestDto) throws JsonProcessingException {
        Long playerId = requestDto.getPlayerId();
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new NullPointerException("해당 플레이어가 존재하지 않습니다"));
        GameRoom gameRoom = player.getGameRoom();
        List<CardsDto> selectedCards = requestDto.getSelectedCards();
        for (CardsDto selectedCard : selectedCards) {
            Card card = cardRepository.findByCardId(selectedCard.getCardId());


        }
return null;
    }
}
