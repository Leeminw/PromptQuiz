package com.ssafy.apm.common.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    // WebSocket 사용하면서 뿌릴 로그
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    // 누군가 게임에 접속했을 경우
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection(event: " + event + ")");
    }

    // 누군가 연결이 끊어졌을 경우에 해당 사용자의 상태를 바꾸기
    // 채널, 게임방 인원 줄이기
    // 만약 게임방에 접속해있었을 경우 게임방 떠나기
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        logger.info("Received a web socket disconnection(event: " + event + ")");
    }
}
