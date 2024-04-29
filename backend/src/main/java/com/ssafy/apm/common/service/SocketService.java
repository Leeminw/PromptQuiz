package com.ssafy.apm.common.service;

public interface SocketService {
    void addSessionId(String sessionId);

    void deleteSession(String sessionId);
}
