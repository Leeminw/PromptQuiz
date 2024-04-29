package com.ssafy.apm.socket.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "session", timeToLive = 36000)
public class SessionEntity {
    @Id
    String sessionId;
    String userId;
    Long uuid;
    Integer type; // 1: 게임 방   2: 채널
}
