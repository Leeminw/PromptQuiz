package com.ssafy.apm.common.listener;

import com.ssafy.apm.common.service.SocketService;
import com.ssafy.apm.common.util.SocketEventUrlParser;

import com.ssafy.apm.dottegi.service.DottegiService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SocketService socketService;
    private final DottegiService dottegiService;
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection(event: " + event + ")");

        String sessionId = parsingSessionIdFromEvent(event);
        socketService.addSession(sessionId);
    }

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        logger.info("Received a new web socket subscribe(event: " + event + ")");

        String sessionId = parsingSessionIdFromEvent(event);
        SocketEventUrlParser parser = new SocketEventUrlParser(parsingUrlFromEvent(event));

        if (parser.isOk()) {
            socketService.editSession(sessionId, parseUserId(event), parser.getUuid(), parser.getType());

        } else {
            logger.info("destination format does not match.");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        logger.info("Received a web socket disconnection(event: " + event + ")");
        try{
            String sessionId = parsingSessionIdFromEvent(event);
            socketService.kickOutUser(sessionId);
            socketService.deleteSession(sessionId);

        }catch (Exception e){
            logger.debug("Socket Disconnect Exception : " + e.getMessage());
        }
    }

    public String parsingSessionIdFromEvent(AbstractSubProtocolEvent event){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        return accessor.getSessionId();
    }

    public String parsingUrlFromEvent(SessionSubscribeEvent event){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        return accessor.getDestination();
    }

    public Long parseUserId(SessionSubscribeEvent event){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        return Long.parseLong(Objects.requireNonNull(accessor.getFirstNativeHeader("userId")));
    }

}
