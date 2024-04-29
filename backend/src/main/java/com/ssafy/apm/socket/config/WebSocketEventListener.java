package com.ssafy.apm.socket.config;

import com.ssafy.apm.socket.service.SocketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    // WebSocket 사용하면서 뿌릴 로그
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    private final SocketService socketService;

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
        Long uuid = 0L;
        Integer type = 1;

        // 세션 정보를 redis에 저장
        socketService.editSession(sessionId, uuid, type);
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

        /*
            todo: 강퇴가 된 사용자는 어디로 입장을 할 것인가?
             다시 그 사용자가 똑같은 장소로 입장한다면?
        */
    }
}
