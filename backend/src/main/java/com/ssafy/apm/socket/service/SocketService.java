package com.ssafy.apm.socket.service;

public interface SocketService {
    void addSession(String sessionId);
    void kickOutUser(String sessionId);
    void deleteSession(String sessionId);
    void editSession(String sessionId, Long uuid, Integer type);
}
