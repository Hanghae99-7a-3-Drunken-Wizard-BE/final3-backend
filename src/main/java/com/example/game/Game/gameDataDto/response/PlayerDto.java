package com.example.game.Game.gameDataDto.response;

import com.example.game.Game.h2Package.Card;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.h2Package.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlayerDto {
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
    private List<CardDetailResponseDto> cardsOnHand;

    public PlayerDto(Player player, List<Card> cards) throws JsonProcessingException{
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

        List<CardDetailResponseDto> cardOnHand = new ArrayList<>();
        for (Card card : cards) {
            cardOnHand.add(new CardDetailResponseDto(card));
        }
        this.cardsOnHand = cardOnHand;
    }
}
