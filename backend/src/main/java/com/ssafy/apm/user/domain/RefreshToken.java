package com.ssafy.apm.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 600)
public class RefreshToken {

    @Id
    private String refreshToken;
    private Long userId;

}
