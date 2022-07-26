package com.example.game.model.user;

import com.example.game.dto.user.SignupRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_player")
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

    @Column
    private Integer winCount;

    @Column
    private Integer loseCount;

    @Column(unique = true)
    private Long kakaoId;

    @Column
    private String roomId;

    @Column
    private String sessionId;

    @Column
    private Boolean isPlaying;

    @Column
    private Integer imageNum;

    public User(SignupRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.nickname = requestDto.getNickname();
        this.email = requestDto.getEmail();
        this.winCount = 0;
        this.loseCount = 0;
        this.imageNum = 0;
    }

    // KakaoService 생성자
    public User(String username, String nickname, String password, String email, Long kakaoId, Integer imageNum) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.kakaoId = kakaoId;
        this.winCount = 0;
        this.loseCount = 0;
        this.imageNum = 0;
    }

    // testRunner 생성자
    public User(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.winCount = 0;
        this.loseCount = 0;
    }
}