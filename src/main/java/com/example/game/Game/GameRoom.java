package com.example.game.Game;


import com.example.game.Game.card.Card;
import com.example.game.Game.player.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GameRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameRoomId;

    @Column
    @OneToMany(mappedBy = "gameRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> deck;

    @Column
    @OneToMany(mappedBy = "gameRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Player> playerList;

}
