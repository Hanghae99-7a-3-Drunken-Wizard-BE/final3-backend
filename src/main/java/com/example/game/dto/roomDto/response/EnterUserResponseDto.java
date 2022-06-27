package com.example.game.dto.roomDto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnterUserResponseDto {
    private String nickname;

    public EnterUserResponseDto(String nickname) {
        this.nickname = nickname;
    }
}
