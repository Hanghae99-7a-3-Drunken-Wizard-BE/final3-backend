package com.example.game.repository.user;

import com.example.game.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByKakaoId(Long kakaoId);
    User findBySessionId(String sessionId);
    List<User> findByRoomId(String roomId);
    List<User> findBySessionIdIsNotNull();
}