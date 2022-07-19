package com.example.game.websocket;

import com.example.game.model.Timestamped;
import com.example.game.model.user.User;
import com.example.game.security.UserDetailsImpl;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class GameRoom extends Timestamped implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String roomId;

    @Column
    private String roomName;

    @Column
    private Long player1;

    @Column
    private Long player2;

    @Column
    private Long player3;

    @Column
    private Long player4;

    public GameRoom(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public GameRoom(GameRoomRequestDto requestDto) {
        this.roomId = UUID.randomUUID().toString();
        this.roomName = requestDto.getRoomName();
    }
}
