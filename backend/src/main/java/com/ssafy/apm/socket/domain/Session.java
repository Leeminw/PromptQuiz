package com.ssafy.apm.socket.domain;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "session", timeToLive = 36000)
public class Session {

    @Id
    private String sessionId;
    private Long userId;
    private String uuid;
    private Integer type; // 1: 게임 방   2: 채널

    public void updateState(String uuid, Integer type) {
        this.uuid = uuid;
        this.type = type;
    }

}
