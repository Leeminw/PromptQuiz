package com.ssafy.apm.gameuser.repository;

import com.ssafy.apm.gameuser.domain.GameUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameUserRepository extends CrudRepository<GameUser, String> {

    Optional<GameUser> findByGameCodeAndUserId(String gameCode, Long UserId);

    Optional<GameUser> findByUserId(Long userId);

    Optional<List<GameUser>> findAllByGameCode(String gameCode);

    Optional<GameUser> findByCode(String code);

}
