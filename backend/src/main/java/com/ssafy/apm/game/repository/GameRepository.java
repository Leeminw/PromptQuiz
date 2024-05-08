package com.ssafy.apm.game.repository;

import com.ssafy.apm.game.domain.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game, String> {
    Optional<Game> findByCode(String code);
    Optional<List<Game>> findAllByChannelCode(String channelCode);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Optional<List<Game>> findAllByTitleContaining(String title);
    Optional<List<Game>> findAllByModeContaining(Integer mode);
    Optional<List<Game>> findAllByStyleContaining(String style);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Optional<List<Game>> findAllByIsTeam(Boolean isTeam);
    Optional<List<Game>> findAllByIsPrivate(Boolean isPrivate);
    Optional<List<Game>> findAllByIsStarted(Boolean isStarted);
    Optional<List<Game>> findAllByTimeLimit(Integer timeLimit);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Optional<List<Game>> findAllByCurRounds(Integer curRounds);
    Optional<List<Game>> findAllByMaxRounds(Integer maxRounds);
    Optional<List<Game>> findAllByCurPlayers(Integer curPlayers);
    Optional<List<Game>> findAllByMaxPlayers(Integer maxPlayers);

}
