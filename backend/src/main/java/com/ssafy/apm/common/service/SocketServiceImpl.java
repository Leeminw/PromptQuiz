package com.ssafy.apm.common.service;

import com.ssafy.apm.common.domain.Session;
import com.ssafy.apm.common.repository.SocketRepository;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SocketServiceImpl implements SocketService {

    private final SocketRepository socketRepository;

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

            // todo: 현재 있는 채널 혹은 게임에서 강퇴시키기
            if (session.getType() == 1) {

            } else {

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
