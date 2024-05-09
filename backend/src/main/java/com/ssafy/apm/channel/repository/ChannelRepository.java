package com.ssafy.apm.channel.repository;

import com.ssafy.apm.channel.domain.Channel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Optional<Channel> findByCode(String code);
}
