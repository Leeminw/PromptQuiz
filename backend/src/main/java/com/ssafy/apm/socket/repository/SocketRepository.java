package com.ssafy.apm.socket.repository;

import com.ssafy.apm.socket.domain.SessionEntity;
import org.springframework.data.repository.CrudRepository;

public interface SocketRepository extends CrudRepository<SessionEntity, Long> {

}
