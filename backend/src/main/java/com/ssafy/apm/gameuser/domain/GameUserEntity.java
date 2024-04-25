package com.ssafy.apm.gameuser.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "game-user")
public class GameUserEntity {
    @Id
    private Long id;
    @Indexed
    private Long gameId;
    @Indexed
    private Long userId;
    private Boolean isHost;
    private Boolean isReady;
    private Integer score;
    private String team;
    private Integer ranking;

}
