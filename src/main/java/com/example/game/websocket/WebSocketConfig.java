package com.example.game.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
//@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final StompHandler stompHandler;
//    public WebSocketConfig(StompHandler stompHandler) {
//        this.stompHandler = stompHandler;
//    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        // 보통 ("/queue", "/topic")으로 하고, 다수에게 메세지를 보낼때는 '/topic/주소', 특정대상에게 메세지를 보낼 때는 '/queue/주소'의 방식을 택하게 된다.
        registry.enableSimpleBroker("/sub");
        // /app은 메세지를 보내는 prefix로 작동하며 클라이언트->서버로 메세지를 보낼때는 다음과 같은 방식을 통하게 된다.
        registry.setApplicationDestinationPrefixes("/pub"); // client.send(`/pub/chat/보낼주소`,{},JSON.stringify(보낼데이터))
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/SufficientAmountOfAlcohol")
                .setAllowedOriginPatterns("*") // 클라이언트에서 접속할 수 있는 웹소켓 주소
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}
