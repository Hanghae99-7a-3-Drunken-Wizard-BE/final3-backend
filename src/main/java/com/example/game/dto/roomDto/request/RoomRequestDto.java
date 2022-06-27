package com.example.game.dto.roomDto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequestDto {
    String title;
    String roomId;
    Long userCount;
    int maxUser;
    String battleType;
}
