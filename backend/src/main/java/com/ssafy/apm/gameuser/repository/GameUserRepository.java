package com.ssafy.apm.gameuser.repository;

import com.ssafy.apm.gameuser.domain.GameUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameUserRepository extends CrudRepository<GameUserEntity, String> {

    Optional<List<GameUserEntity>> findAllByGameCode(String gameCode);

    Optional<GameUserEntity> findByGameCodeAndUserId(String gameCode, Long UserId);

    Optional<GameUserEntity> findByUserId(Long userId);

    Optional<GameUserEntity> findByCode(String code);

}
