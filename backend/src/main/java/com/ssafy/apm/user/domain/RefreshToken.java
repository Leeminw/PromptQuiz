package com.ssafy.apm.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "refreshToken",timeToLive = 14440)
public class RefreshToken {
    @Id
    private String refreshToken;
    private Long userId;
}
