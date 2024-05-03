package com.ssafy.apm.userchannel.repository;

import com.ssafy.apm.userchannel.domain.UserChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChannelRepository extends JpaRepository<UserChannelEntity, Long> {
    List<UserChannelEntity> findAllByChannelId(Long channelId);
    Optional<UserChannelEntity> findByUserId(Long userId);

    Optional<UserChannelEntity> findByUserIdAndChannelId(Long userId, Long channelId);
}
