package com.example.game.controller;

import com.example.game.dto.request.DubCheckRequestDto;
import com.example.game.dto.request.SignupRequestDto;
import com.example.game.service.KakaoUserService;
import com.example.game.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    // 회원가입
    @PostMapping("/user/signup")
    public void signup(@Valid @RequestBody SignupRequestDto requestDto){
        userService.signup(requestDto);
    }

    // 아이디 중복체크
    @PostMapping("/user/dubcheck")
    public boolean dubCheck(@RequestBody DubCheckRequestDto requestDto){
        return userService.dubCheck(requestDto);
    }

    // 카카오 아이디
    @GetMapping("/user/kakao/callback")
    public void kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        kakaoUserService.kakaoLogin(code);
    }

}
