package com.example.game.dto.roomDto.response;

import com.example.game.model.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RoomResponseDto{
    private String title;
    private String roomId;
    private Long userCount;
    private int maxUser;
    private String battleType;
    private LocalDateTime createAt;
    private User user;



    public RoomResponseDto(String title, String roomId, Long userCount, int maxUser, String battleType, LocalDateTime createAt, User user) {
        this.title = title;
        this.roomId = roomId;
        this.userCount = userCount < 0 ? 0 : userCount;
        this.maxUser = maxUser;
        this.battleType = battleType;
        this.createAt = createAt;
        this.user = user;
    }
}
