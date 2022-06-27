package com.example.game.game;

import com.example.game.game.player.Player;
import com.example.game.game.player.UserPlayer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerOnGame {
    public List<Player> playerList (LinkedList<UserPlayer> userInLobby){
        return userInLobby.stream().map(Player::new).collect(Collectors.toList());
    }
}
