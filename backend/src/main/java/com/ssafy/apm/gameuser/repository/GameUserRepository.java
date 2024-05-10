package com.ssafy.apm.gameuser.repository;

import com.ssafy.apm.gameuser.domain.GameUser;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface GameUserRepository extends JpaRepository<GameUser, String> {

    Optional<GameUser> findByGameCodeAndUserId(String gameCode, Long UserId);

    Optional<GameUser> findByUserId(Long userId);

    Optional<List<GameUser>> findAllByGameCode(String gameCode);

    Optional<GameUser> findByCode(String code);

    Boolean existsByUserIdAndGameCode(Long userId, String gameCode);

    Optional<GameUser> findByUserIdAndGameCode(Long userId, String gameCode);

}
