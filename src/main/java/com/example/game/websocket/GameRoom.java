package com.example.game.websocket;

import com.example.game.model.Timestamped;
import com.example.game.model.user.User;
import com.example.game.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
public class GameRoom extends Timestamped implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String roomId;

    @Column
    private String roomName;

    @Column
    @OneToMany
    private List<User> userList;

    @Builder
    public GameRoom(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.userList = new ArrayList<>();
    }

    public GameRoom(GameRoomRequestDto requestDto) {
        this.roomId = UUID.randomUUID().toString();
        this.roomName = requestDto.getRoomName();
        this.userList = new ArrayList<>();
    }

    public GameRoom(String roomId, String roomName, List<User> userList) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.userList = userList;
    }

    public int userCount() {
        return this.userList.size();
    }

    public void addUser(User user) {
        this.userList.add(user);
    }
    public void removeUser(User user) {
        this.userList.remove(user);
    }
}
