package com.example.game.Game;

import com.example.game.Game.player.Player;
import com.example.game.Game.player.User;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerOnGame {
    public List<Player> playerList (LinkedList<User> userInLobby){
        return userInLobby.stream().map(Player::new).collect(Collectors.toList());
    }
}
