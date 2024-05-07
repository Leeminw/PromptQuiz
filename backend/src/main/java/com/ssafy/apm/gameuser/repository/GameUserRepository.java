package com.ssafy.apm.gameuser.repository;

import com.ssafy.apm.gameuser.domain.GameUserEntity;
import com.ssafy.apm.gameuser.dto.response.GameUserDetailResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameUserRepository extends CrudRepository<GameUserEntity, Long> {

    Optional<List<GameUserEntity>> findAllByGameId(Long gameId);

    Optional<GameUserEntity> findByGameIdAndUserId(Long gameId, Long UserId);

    Optional<GameUserEntity> findByUserId(Long userId);

}
