package com.example.game.service;

import com.example.game.dto.request.DubCheckRequestDto;
import com.example.game.dto.request.SignupRequestDto;
import com.example.game.model.User;
import com.example.game.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        return !userRepository.findByUsername(requestDto.getUsername()).isPresent();
    }

}
