package com.example.game.model.room;

import com.example.game.dto.roomDto.request.RoomRequestDto;
import com.example.game.model.Timestamped;
import com.example.game.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Room extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String roomId;

    @Column
    private String title;

    @Column
    private Long userCount;

    @ManyToOne
    @JoinColumn
    private User user;

    @Column
    private int maxUser;

    @Column(nullable = false)
    private String battleType;


    public static Room create(RoomRequestDto roomDto, User user, int maxUser) {
        Room room = new Room();
        room.roomId = UUID.randomUUID().toString();
        room.title = roomDto.getTitle();
        room.user = user;
        room.userCount = 0L;
        room.maxUser = maxUser;
        room.battleType = roomDto.getBattleType();
        return room;
    }
}
