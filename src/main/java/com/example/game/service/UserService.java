package com.example.game.service;

import com.example.game.dto.response.UserResponseDto;
import com.example.game.dto.user.DubCheckRequestDto;
import com.example.game.dto.user.SignupRequestDto;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("존재하는 아이디입니다.");
        }

        User user = new User(requestDto);
        String password = passwordEncoder.encode(requestDto.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }

    public boolean dubCheck(DubCheckRequestDto requestDto) {
        return (!userRepository.findByUsername(requestDto.getUsername()).isPresent());
    }

    public List<UserResponseDto> getConnectedUsers() {
        List<User> users = userRepository.findBySessionIdIsNotNull();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();

        for (User user : users) {
            UserResponseDto userResponseDto = new UserResponseDto(
                    user.getId(),
                    user.getNickname()
            );
            userResponseDtos.add(userResponseDto);
        }
        return userResponseDtos;
    }
}
