package com.ssafy.apm.common.service;

import com.ssafy.apm.channel.service.ChannelService;
import com.ssafy.apm.common.domain.Session;
import com.ssafy.apm.common.repository.SocketRepository;

import com.ssafy.apm.gameuser.service.GameUserService;
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
    private final GameUserService gameUserService;
    private final SocketRepository socketRepository;
    private final UserChannelService userChannelService;

    @Override
    @Transactional
    public void addSession(String sessionId) {
        try {
            socketRepository.save(new Session(sessionId, 1L, "0", 2));
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void kickOutUser(String sessionId) {
        try {
            Session session = socketRepository.findBySessionId(sessionId).orElseThrow();

            if (session.getType() == GAME) {
                gameUserService.deleteGameUser(session.getUuid(), session.getUserId());
            } else {
                userChannelService.deleteExitUserChannelByUserIdAndCode(session.getUserId(), session.getUuid());
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void editSession(String sessionId, String uuid, Integer type) {
        try {
            Session session = socketRepository.findBySessionId(sessionId).orElseThrow();
            session.updateState(uuid, type);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteSession(String sessionId) {
        try {
            Session session = socketRepository.findBySessionId(sessionId).orElseThrow();
            socketRepository.delete(session);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

}
