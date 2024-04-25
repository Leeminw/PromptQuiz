package com.ssafy.apm.user.repository;

import com.ssafy.apm.user.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {
}
