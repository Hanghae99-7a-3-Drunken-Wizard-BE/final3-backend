package com.example.game.Game.card;

import com.example.game.Game.GameRoom;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.CardRepository;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ApplyCardToCharacter {

    private final PlayerRepository playerRepository;
    private final CardRepository cardRepository;

    @Transactional
    public void cardInitiator (Long id, Long targetId, Long cardId){
        Card card = cardRepository.findByCardId(cardId);
        Player player = playerRepository.findById(id).orElseThrow(()->new NullPointerException("플레이어 없음"));
        if (card.getTarget()==Target.ME){
            applyCardtoTarget(player, player, card);
        }
        if (card.getTarget()==Target.SELECT){
            Player targetPlayer = playerRepository.findById(targetId).orElseThrow(()->new NullPointerException("플레이어 없음"));
            applyCardtoTarget(player, targetPlayer, card);
        }
        if (card.getTarget()==Target.ALL) {
            GameRoom gameRoom = player.getGameRoom();
            List<Player> players = playerRepository.findByGameRoom(gameRoom);
            applyCardtoMultipleTarget(player, players, card);
        }
        if (card.getTarget()==Target.ALLY) {
            GameRoom gameRoom = player.getGameRoom();
            List<Player> players = playerRepository.findByGameRoomAndTeam(gameRoom, player.isTeam());
            applyCardtoMultipleTarget(player, players, card);
        }
        if (card.getTarget()==Target.ENEMY) {
            GameRoom gameRoom = player.getGameRoom();
            List<Player> players = playerRepository.findByGameRoomAndTeam(gameRoom, !player.isTeam());
            applyCardtoMultipleTarget(player, players, card);
        }
    }

    @Transactional
    public void applyCardtoTarget (Player player, Player targetPlayer, Card card){
        if (player.damageModifierDuration > 0){targetPlayer.statusUpdateWithDamageModifierPositive(card);}
        else if(player.damageModifierDuration < 0){targetPlayer.statusUpdateWithDamageModifierNegative(card);}
        else{targetPlayer.statusUpdate(card);}
        if(player.manaCostModifierDuration > 0){player.applyManaCostWithModifierPositive(card);}
        else if (player.manaCostModifierDuration < 0) {player.applyManaCostWithModifierNegative(card);}
        else{player.applyManaCost(card);}
        playerRepository.save(player);
        playerRepository.save(targetPlayer);
    }

    @Transactional
    public void applyCardtoMultipleTarget (Player player, List<Player> players, Card card) {
        if (player.damageModifierDuration > 0){for (Player playerInList : players) {
            playerInList.statusUpdateWithDamageModifierPositive(card);}}
        else if(player.damageModifierDuration < 0){for (Player playerInList : players) {
            playerInList.statusUpdateWithDamageModifierNegative(card);}}
        else{for (Player playerInList : players) {
            playerInList.statusUpdate(card);}}
        if(player.manaCostModifierDuration > 0){player.applyManaCostWithModifierPositive(card);}
        else if (player.manaCostModifierDuration < 0) {player.applyManaCostWithModifierNegative(card);}
        else{player.applyManaCost(card);}
        playerRepository.saveAll(players);
        playerRepository.save(player);
    }
}

