package com.ssafy.apm.userchannel.repository;

import com.ssafy.apm.userchannel.domain.UserChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChannelRepository extends JpaRepository<UserChannelEntity, Long> {
    List<UserChannelEntity> findAllByChannelId(Long channelId);
}
