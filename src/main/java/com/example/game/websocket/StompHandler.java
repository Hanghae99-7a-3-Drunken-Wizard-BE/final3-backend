package com.example.game.websocket;

import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.h2Package.GameRoom;
import com.example.game.Game.h2Package.Player;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.GameRoomRepository;
import com.example.game.Game.repository.PlayerRepository;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final GameRoomRepository gameRoomRepository;
    private final GameRepository gameRepository;

    @SneakyThrows
    @Override
    public synchronized Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand() &&
                accessor.getFirstNativeHeader("id") != null) {
            String stringId = accessor.getFirstNativeHeader("id");
            String sessionId = accessor.getSessionId();
            System.out.println(stringId + " 핸들러 preSend 영역" + accessor.getCommand());
            System.out.println(accessor.getSessionId());
            Long id = Long.parseLong(stringId);
            System.out.println(id + " Long 변환 완료");
            System.out.println("StompCommand.CONNECT SessionId : " + sessionId);
            User user = userRepository.findById(id).orElseThrow(
                    () -> new NullPointerException("왜 안되는지 모르겠다")
            );
            System.out.println(user.getUsername());
            user.setSessionId(sessionId);
            userRepository.save(user);
            System.out.println(user.getSessionId() + " : 세션 아이디 저장 완료?");
            ;
            System.out.println(userRepository.findBySessionIdIsNotNull().size() + "커넥트 후 리스트에 남은 유저 수");
        }

        if (StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId = accessor.getSessionId();
            System.out.println("StompCommand.DISCONNECT SessionId : " + sessionId);
            if (sessionId != null) {
                User user = userRepository.findBySessionId(sessionId);
                if (user.getRoomId() != null) {
                    System.out.println("StompCommand.DISCONNECT findBySessionId로 찾은 username: " + user.getUsername());
                    user.setRoomId(null);
                }
                user.setSessionId(null);
                userRepository.save(user);
                System.out.println("DISCONNECT 적용 한 유저: " + user.getUsername() + ", SessionId: " + user.getSessionId() + ", RoomId: " + user.getRoomId());
            }
        }
        return message;
    }
}
