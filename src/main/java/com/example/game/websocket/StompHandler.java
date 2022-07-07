package com.example.game.websocket;

import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.example.game.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final UserRepository userRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println("STOMPHANDLER ACTIVE");

        try {
        if (StompCommand.SUBSCRIBE == accessor.getCommand() ||
                StompCommand.CONNECT == accessor.getCommand()) {
            String username = (String)accessor.getMessageHeaders().get("username");
            User user = userRepository.getByUsername(username);
            System.out.println(username+"조회되는중");
            String sessionId = accessor.getSessionId();
            System.out.println(sessionId+"이건 세션아이디");
            user.setSessionId(sessionId);
            userRepository.save(user);
        }
        } catch (RuntimeException ignored) {}
        return message;
    }
}
