package com.example.game.Game.turn;

import com.example.game.Game.h2Package.Game;
import com.example.game.Game.card.ApplyCardToCharacter;
import com.example.game.Game.h2Package.Card;
import com.example.game.Game.card.Target;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.gameDataDto.request.UseCardDto;
import com.example.game.Game.gameDataDto.subDataDto.DiscardDto;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.h2Package.Player;
import com.example.game.Game.repository.CardRepository;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.PlayerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ActionTurn {
    private final PlayerRepository playerRepository;
    private final CardRepository cardRepository;
    private final GameRepository gameRepository;
    private final ApplyCardToCharacter applyCardToCharacter;
    private final JsonStringBuilder jsonStringBuilder;


    @Transactional("gameTransactionManager")
    public String cardMoveProcess(Long playerId, UseCardDto useCardDto) throws JsonProcessingException {
        Player player = playerRepository.findById(playerId).orElseThrow(
                ()->new NullPointerException("플레이어 없음"));
        List<Player> appliedPlayerList = new ArrayList<>();
        Card card = (useCardDto.getCardId() == 0L) ? null : cardRepository.findByCardId(useCardDto.getCardId());
        if (player.getMutedDuration() > 0) {return "침묵됨";}
        if (useCardDto.getCardId() != null) {
            if (useCardDto.getCardId() == 0L) {
                if (player.getMana() < (-4+manaCostModification(player)) * -1) {
                    return "마나부족";
                }
                Long targetPlayerId = (useCardDto.getTargetPlayerId() != null) ?
                        useCardDto.getTargetPlayerId() : playerId;
                Player targetPlayer = playerRepository.findById(targetPlayerId
                ).orElseThrow(()->new NullPointerException("플레이어 없음"));
                applyCardToCharacter.applyHealerHealtoTarget(player,targetPlayer);

                if (player == targetPlayer) {
                    appliedPlayerList.add(player);
                } else {
                    appliedPlayerList.add(player);
                    appliedPlayerList.add(targetPlayer);
                }
            }
            else {
                System.out.println(player.getUsername());
                System.out.println(card.getCardName() + " 사용되고 있는 카드");

                if(card.manaCost != null) {
                    if (player.getMana() < (card.manaCost+manaCostModification(player)) * -1 && player.getCharactorClass() != CharactorClass.BLOODMAGE) {
                        return "마나부족";
                    }
                }
                Player targetPlayer;
                if (card.getTarget() == Target.ME ||
                        card.getTarget() == Target.ALL ||
                        card.getTarget() == Target.ENEMY ||
                        card.getTarget() == Target.ALLY) {
                    targetPlayer = player;
                } else {
                    if (useCardDto.getTargetPlayerId() != null) {
                        targetPlayer = playerRepository.findById(useCardDto.getTargetPlayerId()).orElseThrow(
                                () -> new NullPointerException("플레이어 없음"));
                    } else {targetPlayer = player;}
                }
                System.out.println("카드 이니시에이터로 전송");
                applyCardToCharacter.cardInitiator(player, targetPlayer, card);
                if (card.getTarget() == Target.ME) {
                    appliedPlayerList.add(player);
                }
                if (card.getTarget() == Target.SELECT) {
                    if (player == targetPlayer) {
                        appliedPlayerList.add(player);
                    } else {
                        appliedPlayerList.add(player);
                        appliedPlayerList.add(targetPlayer);
                    }
                }
                if (card.getTarget() == Target.ALL) {
                    Game game = player.getGame();
                    appliedPlayerList.addAll(playerRepository.findByGame(game));

                }
                if (card.getTarget() == Target.ALLY) {
                    Game game = player.getGame();
                    appliedPlayerList.addAll(playerRepository.findByGameAndTeam(game, player.isTeam()));

                }
                if (card.getTarget() == Target.ENEMY) {
                    Game game = player.getGame();
                    appliedPlayerList.addAll(playerRepository.findByGameAndTeam(game, !player.isTeam()));
                    appliedPlayerList.add(player);
                }
            }
        }
        List<Player> playerTeam = playerRepository.findByGameAndTeam(player.getGame(), player.isTeam());
        List<Player> enemyTeam = playerRepository.findByGameAndTeam(player.getGame(), !player.isTeam());
        boolean ourGameOver = (playerTeam.get(0).isDead() && playerTeam.get(1).isDead());
        boolean theirGameOver = (enemyTeam.get(0).isDead() && enemyTeam.get(1).isDead());
        return jsonStringBuilder.cardUseResponseDtoJsonBuilder(appliedPlayerList, card,ourGameOver||theirGameOver);
    }

    @Transactional("gameTransactionManager")
    public String discard (Long playerId, DiscardDto discardDto) throws JsonProcessingException {
        Player player = playerRepository.getById(playerId);
        Card card = cardRepository.findByCardId(discardDto.getCardId());
        card.addGraveyard();
        player.addMana();
        List<Card> cards = cardRepository.findByLyingPlace(playerId);

        return jsonStringBuilder.discard(playerRepository.getById(playerId), cards, cardRepository.findByCardId(discardDto.getCardId()));
    }

    public int manaCostModification (Player player) {
        if (player.getManaCostModifierDuration() > 0) {return 1;}
        else if (player.getManaCostModifierDuration() < 0) {return -1;}
        else {return 0;}
    }

}
