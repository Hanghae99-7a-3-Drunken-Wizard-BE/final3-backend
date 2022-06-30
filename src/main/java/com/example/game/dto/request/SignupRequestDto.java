package com.example.game.dto.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {
    private String username;
    private String password;
    private String nickname;
    @Email
    private String email;

}
