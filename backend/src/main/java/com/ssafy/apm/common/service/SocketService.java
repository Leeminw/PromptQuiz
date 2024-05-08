package com.ssafy.apm.common.service;

public interface SocketService {

    void addSession(String sessionId);

    void kickOutUser(String sessionId);

    void editSession(String sessionId, String uuid, Integer type);

    void deleteSession(String sessionId);

}