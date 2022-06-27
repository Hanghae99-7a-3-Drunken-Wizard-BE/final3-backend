package com.example.game.Game.player;

import com.example.game.Game.card.Card;
import com.example.game.Game.player.CharactorClass;
import com.example.game.Game.player.PlayerStatus;
import com.example.game.Game.player.User;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
public class Player {

    private Long id;

    private Long gameId;

    private String username;

    private int team;

    private int turnOrder;

    private CharactorClass charactorClass;

    private LinkedList<Card> cardsOnHand = new LinkedList<>();

    private PlayerStatus playerStatus;

    private boolean onReady;

    public Player (User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.playerStatus = new PlayerStatus();
    }
}
