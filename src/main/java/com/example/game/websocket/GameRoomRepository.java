package com.example.game.websocket;

import com.example.game.websocket.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GameRoomRepository extends JpaRepository<GameRoom, Long> {
    List<GameRoom> findAllByOrderByCreatedAtDesc();
    List<GameRoom> findByRoomNameContaining(String keyword);
    GameRoom findByRoomId(String roomId);
}