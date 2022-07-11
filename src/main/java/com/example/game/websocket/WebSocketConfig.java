package com.example.game.websocket;

import com.example.game.security.jwt.JwtDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
//@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    private final JwtDecoder jwtDecoder;
//
//    private static List<String> userList = new ArrayList<>();


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

//    // 웹소켓을 통해 메세지를 보낼때 사용되는 인터셉터
//    @Override
//    public void configureClientInboundChannel(final ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(final Message<?> message, final MessageChannel channel) {
//                StompHeaderAccessor headers = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//                String username = jwtDecoder.decodeUsername(headers.getFirstNativeHeader("Authorization"));
//
//                if (StompCommand.CONNECT == headers.getCommand()) {
//                    System.out.println(username + " 님이 WebSocket에 연결되었습니다.");
//                    if (username != null) {
//                        userList.add(username);
//                        System.out.println(userList + "접속유저 리스트에서 " + username + " 유저를 추가하였습니다." + userList.size() + " 명 접속 중");
//                    }
//                } else if (StompCommand.DISCONNECT == headers.getCommand()) {
//                    System.out.println(username + " 님이 WebSocket에서 연결을 끊었습니다.");
//                    if (username != null) {
//                        userList.remove(username);
//                        System.out.println(userList + "접속유저 리스트에서 " + username + " 유저를 삭제하였습니다." + userList.size() + " 명 접속 중");
//                    }
//                }
//                return message;
//            }
//        });
//    }
}
