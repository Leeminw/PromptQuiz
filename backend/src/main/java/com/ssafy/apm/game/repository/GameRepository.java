package com.ssafy.apm.game.repository;

import com.ssafy.apm.game.domain.GameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, String> {
    Optional<List<GameEntity>> findAllByChannelCode(String channelCode);

    Optional<GameEntity> findByCode(String code);

}
