package com.ssafy.apm.channel.repository;

import com.ssafy.apm.channel.domain.UserChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChannelRepository extends JpaRepository<UserChannelEntity, Long> {
}
