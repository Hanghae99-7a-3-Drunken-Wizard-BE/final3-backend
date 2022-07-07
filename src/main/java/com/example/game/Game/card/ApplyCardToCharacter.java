package com.example.game.Game.card;

import com.example.game.Game.Game;
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
                Game game = player.getGame();
                List<Player> players = playerRepository.findByGameAndTeam(game, player.isTeam());
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
                    Game game = player.getGame();
                    List<Player> players = playerRepository.findByGame(game);
                    applyCardtoMultipleTarget(player, players, card);
                }
                if (card.getTarget() == Target.ALLY) {
                    Game game = player.getGame();
                    List<Player> players = playerRepository.findByGameAndTeam(game, player.isTeam());
                    applyCardtoMultipleTarget(player, players, card);
                }
                if (card.getTarget() == Target.ENEMY) {
                    Game game = player.getGame();
                    List<Player> players = playerRepository.findByGameAndTeam(game, !player.isTeam());
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
        bloodmageManaFeedback(player);
        Game game = gameRepository.findByRoomId(player.getGame().getRoomId());
        game.addTograveyard(card);
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
        bloodmageManaFeedback(player);
        Game game = gameRepository.findByRoomId(player.getGame().getRoomId());
        game.addTograveyard(card);
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
        bloodmageManaFeedback(player);
        Game game = gameRepository.findByRoomId(player.getGame().getRoomId());
        game.addTograveyard(card);
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
        bloodmageManaFeedback(player);
        Game game = gameRepository.findByRoomId(player.getGame().getRoomId());
        game.addTograveyard(card);
        player.removeFromHand(card);
    }

    @Transactional
    public void applyItemtoTarget(Player player, Player targetPlayer, Card card){
        if (targetPlayer.isShield()&&!(player==targetPlayer)){
            targetPlayer.setShield(false);} else{targetPlayer.statusUpdate(card);}
        Game game = gameRepository.findByRoomId(player.getGame().getRoomId());
        game.addTograveyard(card);
        player.removeFromHand(card);
    }

    @Transactional
    public void applyDispel(Player player, Player targetPlayer, Card card){
        if (targetPlayer.isShield()&&!(player==targetPlayer)){
            targetPlayer.setShield(false);} else{targetPlayer.applyAdditionalEffect(card);}
        manaCostApply(player, card);
        bloodmageManaFeedback(player);
        Game game = gameRepository.findByRoomId(player.getGame().getRoomId());
        game.addTograveyard(card);
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

    @Transactional
    public void bloodmageManaFeedback(Player player) {
        if(player.getCharactorClass().equals(CharactorClass.BLOODMAGE) && player.getMana() < 0) {
            player.setHealth(player.getHealth() + player.getMana());
            player.setMana(0);
            player.setDead(player.getHealth() <= 0);
        }
    }
}

