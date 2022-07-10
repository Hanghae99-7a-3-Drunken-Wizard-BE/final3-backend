package com.example.game.Game.turn;

import com.example.game.Game.Game;
import com.example.game.Game.card.ApplyCardToCharacter;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.gameDataDto.request.UseCardDto;
import com.example.game.Game.gameDataDto.subDataDto.DiscardDto;
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
import java.util.List;

@Component
@RequiredArgsConstructor
public class ActionTurn {
    private final PlayerRepository playerRepository;
    private final CardRepository cardRepository;
    private final GameRepository gameRepository;
    private final ApplyCardToCharacter applyCardToCharacter;
    private final JsonStringBuilder jsonStringBuilder;


    @Transactional
    public String cardMoveProcess(Long playerId, UseCardDto useCardDto) throws JsonProcessingException {
        Player player = playerRepository.findById(playerId).orElseThrow(
                ()->new NullPointerException("플레이어 없음"));
        Player targetPlayer = playerRepository.findById(useCardDto.getTargetPlayerId()).orElseThrow(
                ()->new NullPointerException("플레이어 없음"));
        Card card = cardRepository.findByCardId(useCardDto.getCardId());
        applyCardToCharacter.cardInitiator(player,targetPlayer,card);
        if(player.getMana() < card.manaCost * -1 && player.getCharactorClass() != CharactorClass.BLOODMAGE) {
            return "마나부족";
        }
        List<Player> appliedPlayerList = new ArrayList<>();
        if (card.getTarget() == Target.ME) {
            appliedPlayerList.add(player);
        }
        if (card.getTarget() == Target.SELECT) {
            if (player == targetPlayer) {appliedPlayerList.add(player);}
            else{appliedPlayerList.add(player); appliedPlayerList.add(targetPlayer);}
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
        List<Player> playerTeam = playerRepository.findByGameAndTeam(player.getGame(), player.isTeam());
        List<Player> enemyTeam = playerRepository.findByGameAndTeam(player.getGame(), !player.isTeam());
        boolean ourGameOver = (playerTeam.get(0).isDead() && playerTeam.get(1).isDead());
        boolean theirGameOver = (enemyTeam.get(0).isDead() && enemyTeam.get(1).isDead());
        return jsonStringBuilder.cardUseResponseDtoJsonBuilder(appliedPlayerList, ourGameOver||theirGameOver);
    }

    @Transactional
    public String discard (Long playerId, DiscardDto discardDto) throws JsonProcessingException {
        Player player = playerRepository.getById(playerId);
        Game game = gameRepository.findByRoomId(player.getGame().getRoomId());
        Card card = cardRepository.findByCardId(discardDto.getCardId());
        game.addTograveyard(card);
        return jsonStringBuilder.discard(discardDto);
    }

}
