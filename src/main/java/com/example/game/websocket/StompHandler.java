package com.example.game.websocket;

import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.example.game.security.UserDetailsImpl;
import com.example.game.security.jwt.JwtDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final UserRepository userRepository;

    @Override
    public synchronized Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if(StompCommand.CONNECT == accessor.getCommand() &&
                accessor.getFirstNativeHeader("id") != null) {
            String stringId = accessor.getFirstNativeHeader("id");
            String sessionId = accessor.getSessionId();
            System.out.println(stringId + " 핸들러 preSend 영역" + accessor.getCommand());
            System.out.println(accessor.getSessionId());
            Long id = Long.parseLong(stringId);
            System.out.println(id+" Long 변환 완료");
            System.out.println(sessionId+"이건 세션아이디");
            User user = userRepository.findById(id).orElseThrow(
                    ()-> new NullPointerException("왜 안되는지 모르겠다")
            );
            System.out.println(user.getUsername());
            user.setSessionId(sessionId);
            userRepository.save(user);
            System.out.println(user.getSessionId() + "세션 아이디 저장 완료?");
        }

        return message;
    }
}
