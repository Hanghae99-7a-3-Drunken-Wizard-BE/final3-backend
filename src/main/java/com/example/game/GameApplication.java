package com.example.game;

import com.example.game.game.card.ApplyCard;
import com.example.game.game.card.magic.attack.MagicMissile;
import com.example.game.game.player.Player;
import com.example.game.game.player.PlayerStatus;
import com.example.game.game.player.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameApplication {

    public static void main(String[] args) {

        SpringApplication.run(GameApplication.class, args);

        PlayerStatus playerStatus = new PlayerStatus();
        ApplyCard applyCard = new ApplyCard(playerStatus);
        User user1= new User(1L, "user1", "password", 1, 1);
        User user2 = new User(2L, "user2", "password", 1, 1);
        Player player1 = new Player(user1);
        Player player2 = new Player(user2);
        MagicMissile magicMissile = new MagicMissile();
        applyCard.applyCardtoTarget(player1, player2, magicMissile);

    }





}
