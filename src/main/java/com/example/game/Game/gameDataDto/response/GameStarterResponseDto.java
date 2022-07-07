package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.Game;
import com.example.game.Game.card.Card;
import com.example.game.Game.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameStarterResponseDto {
    private List<PlayerDto> players;
    private List<CardMappingDto> cards;

    public GameStarterResponseDto(Game game) throws JsonProcessingException {
        List<PlayerDto> responseDtos = new ArrayList<>();
        List<Player> playerList = game.getPlayerList();
        for(Player player : playerList) {
            responseDtos.add(new PlayerDto(player));
        }
        this.players = responseDtos;
        List<Card> deck = game.getDeck();
        List<CardMappingDto> cards = new ArrayList<>();
        for(Card card : deck) {
            cards.add(new CardMappingDto(card.getCardId(), card.getCardName()));
        }
        this.cards = cards;

    }
}
