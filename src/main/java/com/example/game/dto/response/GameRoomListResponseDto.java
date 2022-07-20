package com.example.game.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GameRoomListResponseDto {
    private List<GameRoomResponseDto> gameRoomList;

}
