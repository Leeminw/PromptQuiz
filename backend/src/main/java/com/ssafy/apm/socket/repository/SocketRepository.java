package com.ssafy.apm.socket.repository;

import com.ssafy.apm.socket.domain.Session;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface SocketRepository extends CrudRepository<Session, Long> {

    void deleteBySessionId(String sessionId);

    Optional<Session> findBySessionId(String sessionId);

}
