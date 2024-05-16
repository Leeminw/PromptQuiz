package com.ssafy.apm.common.service;

import com.ssafy.apm.common.dto.SessionResponseDto;

public interface SocketService {

    SessionResponseDto getSession(String sessionId);

    void addSession(String sessionId);

    void kickOutUser(String sessionId);

    void editSession(String sessionId, Long userId, String uuid, Integer type);

    void deleteSession(String sessionId);

}
