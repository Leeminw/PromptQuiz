package com.ssafy.apm.socket.repository;

import com.ssafy.apm.socket.domain.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SocketRepository extends CrudRepository<Session, Long> {
    void deleteBySessionId(String sessionId);
    Optional<Session> findBySessionId(String sessionId);
}
