package com.example.game.Game.turn;

import com.example.game.Game.GameRoom;
import com.example.game.Game.card.ApplyCardToCharacter;
import com.example.game.Game.card.Card;
import com.example.game.Game.card.Target;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.gameDataDto.request.UseCardDto;
import com.example.game.Game.player.Player;
import com.example.game.Game.repository.CardRepository;
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
    private final ApplyCardToCharacter applyCardToCharacter;
    private final JsonStringBuilder jsonStringBuilder;


    @Transactional
    public String cardMoveProcess(UseCardDto useCardDto) throws JsonProcessingException {
        Player player = playerRepository.findById(useCardDto.getPlayerId()).orElseThrow(
                ()->new NullPointerException("플레이어 없음"));
        Player targetPlayer = playerRepository.findById(useCardDto.getTargetPlayerID()).orElseThrow(
                ()->new NullPointerException("플레이어 없음"));
        Card card = cardRepository.findByCardId(useCardDto.getCardId());
        applyCardToCharacter.cardInitiator(player,targetPlayer,card);
        List<Player> appliedPlayerList = new ArrayList<>();
        if (card.getTarget() == Target.ME) {
            appliedPlayerList.add(player);
        }
        if (card.getTarget() == Target.SELECT) {
            if (player == targetPlayer) {appliedPlayerList.add(player);}
            else{appliedPlayerList.add(player); appliedPlayerList.add(targetPlayer);}
        }
        if (card.getTarget() == Target.ALL) {
            GameRoom gameRoom = player.getGameRoom();
            appliedPlayerList.addAll(playerRepository.findByGameRoom(gameRoom));

        }
        if (card.getTarget() == Target.ALLY) {
            GameRoom gameRoom = player.getGameRoom();
            appliedPlayerList.addAll(playerRepository.findByGameRoomAndTeam(gameRoom, player.isTeam()));

        }
        if (card.getTarget() == Target.ENEMY) {
            GameRoom gameRoom = player.getGameRoom();
            appliedPlayerList.addAll(playerRepository.findByGameRoomAndTeam(gameRoom, !player.isTeam()));
            appliedPlayerList.add(player);
        }
        List<Player> playerTeam = playerRepository.findByGameRoomAndTeam(player.getGameRoom(), player.isTeam());
        List<Player> enemyTeam = playerRepository.findByGameRoomAndTeam(player.getGameRoom(), !player.isTeam());
        boolean ourGameOver = (playerTeam.get(0).isDead() && playerTeam.get(1).isDead());
        boolean theirGameOver = (enemyTeam.get(0).isDead() && enemyTeam.get(1).isDead());
        return jsonStringBuilder.cardUseResponseDtoJsonBuilder(appliedPlayerList, ourGameOver||theirGameOver);
    }

}
