package com.ssafy.apm.gamequiz.repository;

import com.ssafy.apm.gamequiz.domain.GameQuizEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameQuizRepository extends CrudRepository<GameQuizEntity, String> {
    Optional<List<GameQuizEntity>> findAllByGameCode(String gameCode);

    Optional<List<GameQuizEntity>> findAllByGameCodeAndRound(String gameCode, Integer round);

    Optional<GameQuizEntity> findByGameCodeAndRound(String gameCode, Integer round);
}
