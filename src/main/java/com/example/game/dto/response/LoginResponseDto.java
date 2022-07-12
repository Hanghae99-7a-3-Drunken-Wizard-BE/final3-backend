package com.example.game.dto.response;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String username;
    private String nickname;
    private Long id;

    public LoginResponseDto(String username, String nickname, Long id) {
        this.nickname = nickname;
        this.username = username;
        this.id = id;
    }
}
