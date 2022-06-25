package com.example.game.Game.player;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "USERS")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;
    @Column
    private String password;

    @Column
    private String email;

    @Column
    private int winCount;
    @Column
    private int loseCount;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.winCount = 0;
        this.loseCount = 0;
    }
}
