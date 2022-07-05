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

@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final UserDetailsImpl userDetails;
    private final UserRepository userRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        try {
        if (StompCommand.CONNECT == accessor.getCommand()) {
            User user = userRepository.getById(userDetails.getUser().getId());
            String sessionId = accessor.getSessionId();
            user.setSessionId(sessionId);
            userRepository.save(user);
        }
        } catch (RuntimeException ignored) {}
        return message;
    }
}
