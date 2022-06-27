package com.example.game.controller;

import com.example.game.dto.user.DubCheckRequestDto;
import com.example.game.dto.user.SignupRequestDto;
import com.example.game.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
//    private final KakaoUserService kakaoUserService;

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


//    @GetMapping("/user/kakao/callback")
//    public ResponseEntity kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
//
//            kakaoUserService.kakaoLogin(code, response);
//            return new ResponseEntity("카카오 사용자로 로그인 되었습니다", HttpStatus.OK);
//    }

}
