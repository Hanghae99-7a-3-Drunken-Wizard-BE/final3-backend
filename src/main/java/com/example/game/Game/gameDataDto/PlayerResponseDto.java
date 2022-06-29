package com.example.game.Game.gameDataDto;

import com.example.game.Game.card.Card;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PlayerResponseDto {
    private Long playerId;
    private String username;
    private boolean team;
    private int turnOrder;
    private CharactorClass charactorClass;
    private int health;
    private boolean shield;
    private int mana;
    private boolean dead;
    private int poisonedDuration;
    private int stunnedDuration;
    private int mutedDuration;
    private int petrifiedDuration;
    public int damageModifierDuration;
    public int weakDuration;
    public int manaCostModifierDuration;
    private int sleepDuration;
    private String cardsOnHand;

    public PlayerResponseDto(Player player) throws JsonProcessingException{
        this.playerId = player.getPlayerId();

        this.username = player.getUsername();

        this.team = player.isTeam();

        this.turnOrder = player.getTurnOrder();

        this.charactorClass = player.getCharactorClass();

        this.health = player.getHealth();

        this.shield = player.isShield();

        this.mana = player.getMana();

        this.dead = player.isDead();

        this.poisonedDuration = player.getPoisonedDuration();

        this.stunnedDuration = player.getStunnedDuration();

        this.mutedDuration = player.getMutedDuration();

        this.petrifiedDuration = player.getPetrifiedDuration();

        this.damageModifierDuration = player.getDamageModifierDuration();

        this.weakDuration = player.weakDuration;

        this.manaCostModifierDuration = player.getManaCostModifierDuration();

        this.sleepDuration = player.getSleepDuration();

        List<CardsResponseDto> cardIds = new ArrayList<>();
        List<Card> cards = player.getCardsOnHand();
        for (Card card : cards) {
            cardIds.add(new CardsResponseDto(card.getCardId()));
        }
        ObjectWriter ow = new ObjectMapper().writer();
        this.cardsOnHand = ow.writeValueAsString(cardIds);
    }
}
