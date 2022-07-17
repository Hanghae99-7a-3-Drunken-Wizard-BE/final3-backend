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

    public void cardInitiator (Player player, Player targetPlayer, Card card){
        if(card.getCardType().equals(CardType.EVENT)){/*스페셜 카드의 적용을 위해 공백으로 남김*/}
        else if (card.getCardId() == 0L) {applyHealerHealtoTarget(player, targetPlayer);}
        else {
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

    public void applyCardtoTarget (Player player, Player targetPlayer, Card card){
        newStatusUpdate(player, targetPlayer, card);
        newApplyManaCost(player,card);
        bloodmageManaFeedback(player);
        card.addGraveyard();
    }

    public void applyHealerHealtoTarget(Player player, Player targetPlayer) {
        if (player.damageModifierDuration > 0){if (targetPlayer.isShield()&&!(player==targetPlayer)){
            targetPlayer.setShield(false);} else{targetPlayer.applyHealerHealWithDamageModifierPositive();}}
        else if(player.damageModifierDuration < 0){if (targetPlayer.isShield()&&!(player==targetPlayer)){
            targetPlayer.setShield(false);} else{targetPlayer.applyHealerHealWithDamageModifierNegative();}}
        else{if (targetPlayer.isShield()&&!(player==targetPlayer)){
            targetPlayer.setShield(false);} else{targetPlayer.applyHealerHeal();}}
        healerHealmanaCostApply(player);
    }

    public void applyCardtoMultipleTarget (Player player, List<Player> players, Card card) {
        for (Player playerInList : players) {
            newStatusUpdate(player, playerInList, card);}
        newApplyManaCost(player,card);
        bloodmageManaFeedback(player);
        card.addGraveyard();
    }

    public void healerHealmanaCostApply(Player player) {
        if(player.manaCostModifierDuration > 0){player.applyhealerHealManaCostWithModifierPositive();}
        else if (player.manaCostModifierDuration < 0) {player.applyhealerHealManaCostWithModifierNegative();}
        else{player.applyhealerHealManaCost();}
    }

    public void newStatusUpdate (Player player, Player targetPlayer, Card card) {
        if (targetPlayer.isShield() && player != targetPlayer) {
            targetPlayer.setShield(false);
        } else {
            if (targetPlayer.getPetrifiedDuration() <= 0) {
                targetPlayer.setHealth(
                        targetPlayer.getHealth() +
                                card.getHealthModifier() +
                                damageModification(player, targetPlayer, card)
                );
                targetPlayer.setMana(targetPlayer.getMana()+card.getManaModifier()) ;
                targetPlayer.setDead(targetPlayer.getHealth() <=0);
                targetPlayer.applyAdditionalEffect(card);
            }
        }
    }

    public void newApplyManaCost (Player player, Card card) {
        player.setMana(player.getMana() + card.getManaCost() + manaModification(player, card));
    }

    public int damageModification (Player player, Player targetPlayer, Card card) {
        if (card.getHealthModifier() == null) {return 0;}
        int armorWeakCheck;
        if (targetPlayer.getWeakDuration()>0){
            armorWeakCheck = -1;
        } else if (targetPlayer.getWeakDuration()<0) {
            armorWeakCheck = 1;
        } else {armorWeakCheck = 0;}
        int amplificationAttenuationCheck;
        if (player.getDamageModifierDuration() > 0) {
            amplificationAttenuationCheck = -1;
        } else if (player.getDamageModifierDuration() < 0) {
            amplificationAttenuationCheck = 1;
        } else {amplificationAttenuationCheck = 0;}
        if (card.getHealthModifier() < 0) {return amplificationAttenuationCheck + armorWeakCheck;}
        else if (card.getHealthModifier() > 0) {return amplificationAttenuationCheck * -1;}
        else if (card.getCardType().equals(CardType.ITEM)) {return 0;}
        else {return 0;}
    }

    public int manaModification (Player player, Card card) {
        if (card.getManaCost() == null) {return 0;}
        int healerAdventage;
        int manaCostModCheck;
        if (player.getManaCostModifierDuration() > 0) {manaCostModCheck = 1;}
        else if (player.getManaCostModifierDuration() < 0) {manaCostModCheck = -1;}
        else {manaCostModCheck = 0;}
        if (player.getCharactorClass() == CharactorClass.HEALER
                && (card.getCardName().equals("Heal") || card.getCardName().equals("PartyHeal"))) {
            healerAdventage = 1;
        } else {healerAdventage = 0;}
        return healerAdventage + manaCostModCheck;
    }

    public void bloodmageManaFeedback(Player player) {
        if(player.getCharactorClass().equals(CharactorClass.BLOODMAGE) && player.getMana() < 0) {
            player.setHealth(player.getHealth() + player.getMana());
            player.setMana(0);
            player.setDead(player.getHealth() <= 0);
        }
    }
}

