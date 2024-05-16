package com.ssafy.apm.common.service;

import com.ssafy.apm.common.domain.Session;
import com.ssafy.apm.game.service.GameService;
import com.ssafy.apm.common.dto.SessionResponseDto;
import com.ssafy.apm.common.repository.SocketRepository;
import com.ssafy.apm.userchannel.service.UserChannelService;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SocketServiceImpl implements SocketService {

    private static final Integer GAME = 1, CHANNEL = 2;
    private final GameService gameService;
    private final SocketRepository socketRepository;
    private final UserChannelService userChannelService;

    @Override
    public SessionResponseDto getSession(String sessionId) {
        Session session = socketRepository.findBySessionId(sessionId).orElseThrow(
                () -> new RuntimeException("Session not found " + sessionId));

        return new SessionResponseDto(session);
    }

    @Override
    @Transactional
    public void addSession(String sessionId) {
        if(!socketRepository.existsSessionBySessionId(sessionId)){
            socketRepository.save(new Session(sessionId, 1L, "0", 2));
        }
    }

    @Override
    @Transactional
    public void kickOutUser(String sessionId) {
        Session session = socketRepository.findBySessionId(sessionId).orElseThrow(
                () -> new RuntimeException("Session not found " + sessionId));

        if(!session.getUuid().equals("0")){
            if (session.getType().equals(GAME)) {
                gameService.exitGameByUserId(session.getUserId(),session.getUuid());
            } else {
                userChannelService.deleteExitUserChannelByUserIdAndCode(session.getUserId(), session.getUuid());
            }
        }
    }

    @Override
    @Transactional
    public void editSession(String sessionId, Long userId, String uuid, Integer type) {
        Session session = socketRepository.findBySessionId(sessionId).orElseThrow(
                () -> new RuntimeException("Session not found " + sessionId));

        session.updateState(userId, uuid, type);
        socketRepository.save(session);
    }

    @Override
    @Transactional
    public void deleteSession(String sessionId) {
        Session session = socketRepository.findBySessionId(sessionId).orElseThrow(
                () -> new RuntimeException("Session not found " + sessionId));
        socketRepository.delete(session);
    }

}
