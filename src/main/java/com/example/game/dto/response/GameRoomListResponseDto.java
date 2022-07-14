package com.example.game.dto.response;

import com.example.game.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GameRoomListResponseDto {
    private List<GameRoomResponseDto> gameRoomList;
}
