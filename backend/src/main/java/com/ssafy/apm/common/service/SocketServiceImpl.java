package com.ssafy.apm.common.service;

import com.ssafy.apm.common.domain.Session;
import com.ssafy.apm.common.repository.SocketRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SocketServiceImpl implements SocketService {

    private final SocketRepository socketRepository;

    @Override
    @Transactional
    public void addSession(String sessionId) {
        socketRepository.save(new Session(sessionId, 1L, "0", 2));
    }

    @Override
    @Transactional
    public void kickOutUser(String sessionId) {

        Session session = socketRepository.findBySessionId(sessionId).orElseThrow();

        // todo: 현재 있는 채널 혹은 게임에서 강퇴시키기
        if (session.getType() == 1) {

        } else {

        }
    }

    @Override
    @Transactional
    public void editSession(String sessionId, String uuid, Integer type) {
        Session session = socketRepository.findBySessionId(sessionId).orElseThrow();
        session.updateState(uuid, type);
    }

    @Override
    @Transactional
    public void deleteSession(String sessionId) {
        Session session = socketRepository.findBySessionId(sessionId).orElseThrow();
        socketRepository.delete(session);
    }

}
