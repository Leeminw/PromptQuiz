package com.ssafy.apm.userchannel.repository;

import com.ssafy.apm.userchannel.domain.UserChannel;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChannelRepository extends JpaRepository<UserChannel, Long> {
    Optional<UserChannel> findByUserId(Long userId);

    Optional<UserChannel> findByUserIdAndChannelId(Long userId, Long channelId);

    Optional<List<UserChannel>> findAllByUserId(Long userId);

    List<UserChannel> findAllByChannelId(Long channelId);

    Boolean existsByUserId(Long userId);

    Integer countByChannelId(Long channelId);
}
