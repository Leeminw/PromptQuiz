package com.ssafy.apm.userchannel.repository;

import com.ssafy.apm.userchannel.domain.UserChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChannelRepository extends JpaRepository<UserChannel, Long> {
    List<UserChannel> findAllByChannelId(Long channelId);
    Optional<UserChannel> findByUserId(Long userId);
    Optional<UserChannel> findByUserIdAndChannelId(Long userId, Long channelId);
}
