package com.example.game.Game.card;

import com.example.game.Game.player.Player;
import com.example.game.Game.player.PlayerStatus;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ApplyCard {

    private final PlayerStatus playerStatus;

    public void applyCardtoSelf (Player player, Card card){
        player.setPlayerStatus(new PlayerStatus(player.getPlayerStatus(), card));
        player.setPlayerStatus(playerStatus.manaUse(player.getPlayerStatus(), card));
    }

    public void applyCardtoTarget (Player player, Player targetPlayer, Card card) {
        targetPlayer.setPlayerStatus(new PlayerStatus(targetPlayer.getPlayerStatus(), card));
        player.setPlayerStatus(playerStatus.manaUse(player.getPlayerStatus(), card));
        System.out.println(player.getUsername()+"이(가)"+targetPlayer.getUsername()+"를 "
                +card.getCardName()+"으로 "+card.cardType+"했습니다");
        System.out.println(player.getUsername()+"의 상태 :");
        System.out.println("체력 : "+player.getPlayerStatus().getHealth());
        System.out.println("마나 : "+player.getPlayerStatus().getMana());
        System.out.println(targetPlayer.getUsername()+"의 상태 :");
        System.out.println("체력 : "+targetPlayer.getPlayerStatus().getHealth());
        System.out.println("마나 : "+targetPlayer.getPlayerStatus().getMana());
    }

    public void applyCardtoAll (Player player, List<Player> players, Card card) {
        for (Player playerInList : players) {
            playerInList.setPlayerStatus(new PlayerStatus(playerInList.getPlayerStatus(), card));
            player.setPlayerStatus(playerStatus.manaUse(player.getPlayerStatus(), card));
        }
    }

    public void applyCardtoEnemys (Player player, List<Player> players, Card card) {
        List<Player> enemys = players.stream().filter(p -> p.getTeam()!=player.getTeam()).collect(Collectors.toList());
        for(Player enemy : players) {
            enemy.setPlayerStatus(new PlayerStatus(enemy.getPlayerStatus(), card));
        }
        player.setPlayerStatus(playerStatus.manaUse(player.getPlayerStatus(), card));
    }

    public void applyCardtoAllys (Player player, List<Player> players, Card card) {
        List<Player> allys = players.stream().filter(p -> p.getTeam()==player.getTeam()).collect(Collectors.toList());
        for(Player ally : players) {
            ally.setPlayerStatus(new PlayerStatus(ally.getPlayerStatus(), card));
        }
        player.setPlayerStatus(playerStatus.manaUse(player.getPlayerStatus(), card));
    }
}
