package com.ssafy.apm.channel.repository;

import com.ssafy.apm.channel.domain.ChannelEntity;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {
    Optional<ChannelEntity> findByCode(String code);
}
