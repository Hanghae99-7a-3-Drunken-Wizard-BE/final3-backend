package com.example.game.dto.response;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String username;
    private String nickname;

    public LoginResponseDto(String username, String nickname) {
        this.nickname = nickname;
        this.username = username;
    }
}
