package com.example.game.model.user;

import com.example.game.dto.user.SignupRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Random;

@Entity
@Getter
@Setter
@Table(name = "USER_PLAYER")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;        // 아이디

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(unique = true)
    private Long kakaoId;

    public User(SignupRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.nickname = requestDto.getNickname();
        this. email = requestDto.getEmail();
    }

    // KakaoService 생성자
    public User(String username, String nickname, String password, String email, Long kakaoId){
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.kakaoId = kakaoId;
    }

    // testRunner 생성자
    public User(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

}