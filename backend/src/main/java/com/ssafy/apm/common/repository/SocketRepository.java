package com.ssafy.apm.common.repository;

import com.ssafy.apm.common.domain.SessionEntity;
import org.springframework.data.repository.CrudRepository;

public interface SocketRepository extends CrudRepository<SessionEntity, Long> {
}
