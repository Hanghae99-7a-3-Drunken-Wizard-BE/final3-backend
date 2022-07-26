package com.example.game.service;

import com.example.game.dto.KakaoUserInfoDto;
import com.example.game.dto.response.LoginResponseDto;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.example.game.security.UserDetailsImpl;
import com.example.game.security.jwt.JwtTokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @Transactional
    public void kakaoLogin(String code, HttpServletResponse response) throws IOException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. "카카오 사용자 정보"로 필요시 회원가입
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

        // 4. 강제 로그인 처리
        forceLogin(kakaoUser, response);
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "a726ea61587396de89413a1bf0c9771b");              // rest api 키
        body.add("redirect_uri", "http://localhost:3000/auth/kakao/callback");  // 플랫폼
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();

        String nickname = jsonNode.get("properties")
                .get("nickname").asText();

        String email = (jsonNode.get("kakao_account")
                .get("email") != null) ? jsonNode.get("kakao_account")
                .get("email").asText() : null;

        return new KakaoUserInfoDto(id, nickname, email);
    }

    private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);
        if (kakaoUser == null) {
            // 회원가입
            // username: kakao nickname
            String username = UUID.randomUUID().toString();
            String nickname = kakaoUserInfo.getNickname();

            // password: random UUID
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            // email: kakao email
            String email = kakaoUserInfo.getEmail();

            Random imageNum = new Random();

            kakaoUser = new User(username ,nickname, encodedPassword, email, kakaoId, imageNum.nextInt(5));
            userRepository.save(kakaoUser);
        }
        return kakaoUser;
    }

    private void forceLogin(User kakaoUser, HttpServletResponse response) throws IOException {

        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails1 = ((UserDetailsImpl) authentication.getPrincipal());

        final String token = JwtTokenUtils.generateJwtToken(userDetails1);

        response.addHeader("Authorization", "BEARER" + " " + token);

        // 헤더에 유저정보 같이 넣었지만 한글이 안나옴 다른곳에 넣어야함

        response.setContentType("application/json; charset=utf-8");
        User user = userDetails1.getUser();
        LoginResponseDto loginResponseDto = new LoginResponseDto(user.getUsername(), user.getNickname(), user.getId(), user.getImageNum());
        String result = mapper.writeValueAsString(loginResponseDto);
        response.getWriter().write(result);

        System.out.println("로그인 내려주는 값" + result);
    }
}
