package com.ssafy.apm.socket.config;

import com.ssafy.apm.socket.service.SocketService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final SocketService socketService;
    private final Integer GAME = 1, CHANNEL = 2;
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    // 누군가 게임에 접속했을 경우
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection(event: " + event + ")");

        // event 객체에서 sessionId 추출
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        // 세션 정보를 redis에 저장
        socketService.addSession(sessionId);
    }

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        logger.info("Received a new web socket subscribe(event: " + event + ")");

        // event 객체에서 sessionId 추출
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String url = accessor.getDestination();

        // destination parsing
        Pattern pattern = Pattern.compile("/ws/sub/(\\w+)\\?uuid=(-?\\d+)");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            String type = matcher.group(1); // game 문자열 가져오기
            String uuid = matcher.group(2); // uuid 값 가져오기

            // 구독한 정보를 세션에 저장
            socketService.editSession(sessionId, uuid, type.equals("game") ? GAME : CHANNEL);

        } else {
            logger.info("destination format does not match.");
        }
    }

    // 세션 연결이 끊어졌을 경우
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        logger.info("Received a web socket disconnection(event: " + event + ")");

        // event 객체에서 sessionId 추출
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        // 현재 있는 채널에서 삭제하기
        socketService.kickOutUser(sessionId);

        // 세션 정보를 삭제하고 퇴장시키기
        socketService.deleteSession(sessionId);
    }
}
