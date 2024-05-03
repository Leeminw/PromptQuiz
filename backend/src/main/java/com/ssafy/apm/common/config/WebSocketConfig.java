package com.ssafy.apm.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 브로커가 보내줄 주소 즉 클라이언트가 받게될 주소 설정과 서버에서 입력을 받게 될 prefix를 설정한다
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/ws/sub");
        config.setApplicationDestinationPrefixes("/ws/pub");
    }

    // connection이 맺어지면 CORS를 허용해주기
    // 쉽게 처음 웹 소켓을 연결하면서 어떤 url로 접근할 것인가를 정하는 것
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
            .addEndpoint("/ws/socket/connect")
            .setAllowedOrigins("http://localhost:3000")
            .withSockJS();
    }
}
