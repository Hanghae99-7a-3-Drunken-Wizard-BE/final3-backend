package com.example.game.Game.player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private String password;
    private int winCount;
    private int loseCount;

    public User(Long id, String username, String password, int winCount, int loseCount) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.winCount = winCount;
        this.loseCount = loseCount;
    }
}
