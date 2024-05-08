package com.ssafy.apm.gameuser.domain;

import com.ssafy.apm.gameuser.dto.request.GameUserUpdateRequestDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(timeToLive = 3600)
public class GameUser {
    @Id
    private String code;
    @Indexed
    private String gameCode;
    @Indexed
    private Long userId;
    private String team;
    private Integer score;
    private Boolean isHost;

    public GameUser update(GameUserUpdateRequestDto requestDto) {
        if (requestDto.getGameCode() != null) this.gameCode = requestDto.getGameCode();
        if (requestDto.getUserId() != null) this.userId = requestDto.getUserId();
        if (requestDto.getTeam() != null) this.team = requestDto.getTeam();
        if (requestDto.getScore() != null) this.score = requestDto.getScore();
        if (requestDto.getIsHost() != null) this.isHost = requestDto.getIsHost();
        return this;
    }

    //    팀 상태 변경
    public GameUser updateTeam(String team) {
        this.team = team;
        return this;
    }

    //    정답, 오답시 스코어 더해주는 API
    public GameUser updateScore(Integer score) {
        this.score += score;
        return this;
    }

    public GameUser updateIsHost(Boolean isHost) {
        this.isHost = isHost;
        return this;
    }

}
