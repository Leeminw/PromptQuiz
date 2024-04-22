package com.ssafy.apm.gameuser.repository;

import com.ssafy.apm.gameuser.domain.GameUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameUserRepository extends JpaRepository<GameUserEntity, Long> {
}
