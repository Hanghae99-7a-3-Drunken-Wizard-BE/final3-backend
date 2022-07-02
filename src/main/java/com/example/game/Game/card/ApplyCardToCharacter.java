package com.example.game.Game.card;

import com.example.game.Game.GameRoom;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.CardRepository;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplyCardToCharacter {

    private final PlayerRepository playerRepository;
    private final CardRepository cardRepository;
    private final GameRepository gameRepository;

    @Transactional
    public void cardInitiator (Player player, Player targetPlayer, Card card){
        if(card.getCardType().equals(CardType.EVENT)){/*스페셜 카드의 적용을 위해 공백으로 남김*/}
        else if(card.getCardType().equals(CardType.ITEM)){applyItemtoTarget(player, targetPlayer, card);}
        else {
            if (card.getCardName().equals("Heal")) {
                applyHealtoTarget(player, targetPlayer, card);
            } else if (card.getCardName().equals("Party Heal")) {
                GameRoom gameRoom = player.getGameRoom();
                List<Player> players = playerRepository.findByGameRoomAndTeam(gameRoom, player.isTeam());
                applyHealtoMultipleTarget(player, players, card);
            } else if (card.getCardName().equals("Dispel")) {
                applyDispel(player, targetPlayer, card);
            } else {
                if (card.getTarget() == Target.ME) {
                    applyCardtoTarget(player, player, card);
                }
                if (card.getTarget() == Target.SELECT) {
                    applyCardtoTarget(player, targetPlayer, card);
                }
                if (card.getTarget() == Target.ALL) {
                    GameRoom gameRoom = player.getGameRoom();
                    List<Player> players = playerRepository.findByGameRoom(gameRoom);
                    applyCardtoMultipleTarget(player, players, card);
                }
                if (card.getTarget() == Target.ALLY) {
                    GameRoom gameRoom = player.getGameRoom();
                    List<Player> players = playerRepository.findByGameRoomAndTeam(gameRoom, player.isTeam());
                    applyCardtoMultipleTarget(player, players, card);
                }
                if (card.getTarget() == Target.ENEMY) {
                    GameRoom gameRoom = player.getGameRoom();
                    List<Player> players = playerRepository.findByGameRoomAndTeam(gameRoom, !player.isTeam());
                    applyCardtoMultipleTarget(player, players, card);
                }
            }
        }
    }

    @Transactional
    public void applyHealtoTarget(Player player, Player targetPlayer, Card card) {
        if (player.damageModifierDuration > 0){if (targetPlayer.isShield()&&!(player==targetPlayer)){
            targetPlayer.setShield(false);} else{targetPlayer.applyHealWithDamageModifierPositive(card);}}
        else if(player.damageModifierDuration < 0){if (targetPlayer.isShield()&&!(player==targetPlayer)){
            targetPlayer.setShield(false);} else{targetPlayer.applyHealWithDamageModifierNegative(card);}}
        else{if (targetPlayer.isShield()&&!(player==targetPlayer)){
            targetPlayer.setShield(false);} else{targetPlayer.applyHeal(card);}}
        if (player.getCharactorClass().equals(CharactorClass.HEALER)){healManaCostApplyForHealer(player, card);}
        else{manaCostApply(player, card);}
        GameRoom gameRoom = gameRepository.findByGameRoomId(player.getGameRoom().getGameRoomId());
        gameRoom.addTograveyard(card);
        player.removeFromHand(card);
    }

    @Transactional
    public void applyCardtoTarget (Player player, Player targetPlayer, Card card){
        if (player.damageModifierDuration > 0){if (targetPlayer.isShield()&&!(player==targetPlayer)){
            targetPlayer.setShield(false);} else{targetPlayer.statusUpdateWithDamageModifierPositive(card);}}
        else if(player.damageModifierDuration < 0){if (targetPlayer.isShield()&&!(player==targetPlayer)){
            targetPlayer.setShield(false);} else{targetPlayer.statusUpdateWithDamageModifierNegative(card);}}
        else{if (targetPlayer.isShield()&&!(player==targetPlayer)){
            targetPlayer.setShield(false);} else{targetPlayer.statusUpdate(card);}}
        manaCostApply(player, card);
        GameRoom gameRoom = gameRepository.findByGameRoomId(player.getGameRoom().getGameRoomId());
        gameRoom.addTograveyard(card);
        player.removeFromHand(card);
    }

    @Transactional
    public void applyCardtoMultipleTarget (Player player, List<Player> players, Card card) {
        if (player.damageModifierDuration > 0){for (Player playerInList : players) {
            if (playerInList.isShield()&&!(player==playerInList)){
                playerInList.setShield(false);} else{playerInList.statusUpdateWithDamageModifierPositive(card);}}}
        else if(player.damageModifierDuration < 0){for (Player playerInList : players) {
            if (playerInList.isShield()&&!(player==playerInList)){
                playerInList.setShield(false);} else{playerInList.statusUpdateWithDamageModifierNegative(card);}}}
        else{for (Player playerInList : players) {
            if (playerInList.isShield()&&!(player==playerInList)){
                playerInList.setShield(false);} else{playerInList.statusUpdate(card);}}}
        manaCostApply(player, card);
        GameRoom gameRoom = gameRepository.findByGameRoomId(player.getGameRoom().getGameRoomId());
        gameRoom.addTograveyard(card);
        player.removeFromHand(card);
    }

    @Transactional
    public void applyHealtoMultipleTarget (Player player, List<Player> players, Card card) {
        if (player.damageModifierDuration > 0){for (Player playerInList : players) {
            if (playerInList.isShield()&&!(player==playerInList)){
                playerInList.setShield(false);} else{playerInList.applyHealWithDamageModifierPositive(card);}}}
        else if(player.damageModifierDuration < 0){for (Player playerInList : players) {
            if (playerInList.isShield()&&!(player==playerInList)){
                playerInList.setShield(false);} else{playerInList.applyHealWithDamageModifierNegative(card);}}}
        else{for (Player playerInList : players) {
            if (playerInList.isShield()&&!(player==playerInList)){
                playerInList.setShield(false);} else{playerInList.applyHeal(card);}}}
        if (player.getCharactorClass().equals(CharactorClass.HEALER)){healManaCostApplyForHealer(player, card);}
        else{manaCostApply(player, card);}
        GameRoom gameRoom = gameRepository.findByGameRoomId(player.getGameRoom().getGameRoomId());
        gameRoom.addTograveyard(card);
        player.removeFromHand(card);
    }

    @Transactional
    public void applyItemtoTarget(Player player, Player targetPlayer, Card card){
        if (targetPlayer.isShield()&&!(player==targetPlayer)){
            targetPlayer.setShield(false);} else{targetPlayer.statusUpdate(card);}
        GameRoom gameRoom = gameRepository.findByGameRoomId(player.getGameRoom().getGameRoomId());
        gameRoom.addTograveyard(card);
        player.removeFromHand(card);
    }

    @Transactional
    public void applyDispel(Player player, Player targetPlayer, Card card){
        if (targetPlayer.isShield()&&!(player==targetPlayer)){
            targetPlayer.setShield(false);} else{targetPlayer.applyAdditionalEffect(card);}
        if(player.manaCostModifierDuration > 0){player.applyManaCostWithModifierPositive(card);}
        else if (player.manaCostModifierDuration < 0) {player.applyManaCostWithModifierNegative(card);}
        else{player.applyManaCost(card);}
        GameRoom gameRoom = gameRepository.findByGameRoomId(player.getGameRoom().getGameRoomId());
        gameRoom.addTograveyard(card);
        player.removeFromHand(card);
    }

    @Transactional
    public void manaCostApply(Player player, Card card){
        if(player.manaCostModifierDuration > 0){player.applyManaCostWithModifierPositive(card);}
        else if (player.manaCostModifierDuration < 0) {player.applyManaCostWithModifierNegative(card);}
        else{player.applyManaCost(card);}
    }

    @Transactional
    public void healManaCostApplyForHealer(Player player, Card card){
        if(player.manaCostModifierDuration > 0){player.applyHealManaCostWithModifierPositiveForHealer(card);}
        else if (player.manaCostModifierDuration < 0) {player.applyHealManaCostWithModifierNegativeForHealer(card);}
        else{player.applyHealManaCostForHealer(card);}
    }
}

