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
@RedisHash(value = "game-user", timeToLive = 600)
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

    //    정답, 오답시 스코어 더해주는 API
    public void updateScore(Integer score) {
        this.score += score;
    }

    //    레디 상태 변경
    public void updateIsReady(Boolean isReady) {
        this.isReady = isReady;
    }

    //    팀 상태 변경
    public void updateTeam(String team) {
        this.team = team;
    }

    public void updateIsHost(Boolean isHost) {
        this.isHost = isHost;
    }

}
