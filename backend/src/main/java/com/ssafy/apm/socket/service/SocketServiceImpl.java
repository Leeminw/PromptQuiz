package com.ssafy.apm.socket.service;

import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.socket.domain.Session;
import com.ssafy.apm.user.service.UserService;
import com.ssafy.apm.socket.repository.SocketRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SocketServiceImpl implements SocketService {

    private final UserService userService;
    private final SocketRepository socketRepository;

    @Override
    public void printSession(String sessionId) {
        // 디버깅용
        System.out.println(socketRepository.findBySessionId(sessionId));
    }

    @Override
    @Transactional
    public void addSession(String sessionId) {
        //User user = userService.loadUser();

        // 처음 접속했을때는 일단 유저의 이름과 세션 아이디만 함께 저장을 해놓는다.
        socketRepository.save(new Session(sessionId, "ssafy", "0", 2));
    }

    @Override
    @Transactional
    public void kickOutUser(String sessionId) {
        // todo: 현재 있는 채널 혹은 게임에서 강퇴시키기
        Session session = socketRepository.findBySessionId(sessionId).orElseThrow();

        // 현재 채널인 경우
        if(session.getType() == 1){
            
        // 현재 게임방인 경우
        }else{
            
        }
    }

    @Override
    @Transactional
    public void deleteSession(String sessionId) {
        Session session = socketRepository.findBySessionId(sessionId).orElseThrow();
        socketRepository.delete(session);
    }

    @Override
    @Transactional
    public void editSession(String sessionId, String uuid, Integer type) {
        Session session = socketRepository.findBySessionId(sessionId).orElseThrow();
        session.updateState(uuid, type);
    }
}
