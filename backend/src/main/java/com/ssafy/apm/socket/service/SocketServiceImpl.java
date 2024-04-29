package com.ssafy.apm.socket.service;

import com.ssafy.apm.socket.domain.SessionEntity;
import com.ssafy.apm.socket.repository.SocketRepository;
import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.service.UserService;
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
    public void addSession(String sessionId) {
        User user = userService.loadUser();

        // 처음 접속했을때는 일단 유저의 이름과 세션 아이디만 함께 저장을 해놓는다.
        socketRepository.save(new SessionEntity(sessionId, user.getUserName(), 0L, 2));
    }

    @Override
    public void kickOutUser(String sessionId) {
        // todo: 현재 있는 채널 혹은 게임에서 강퇴시키기
    }

    @Override
    public void deleteSession(String sessionId) {
        // todo: 먼저 세션 Entity를 확인해서 게임방 or 채널에 끊김 액션을 취하고 삭제하기
    }

    @Override
    public void editSession(String sessionId, Long uuid, Integer type) {
        // todo: 채널 이동이므로 상태 바꿔주기
    }
}
