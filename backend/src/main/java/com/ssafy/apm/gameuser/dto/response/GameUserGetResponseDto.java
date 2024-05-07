package com.ssafy.apm.gameuser.dto.response;

import com.ssafy.apm.gameuser.domain.GameUserEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameUserGetResponseDto {
    private String code;
    private String gameCode;
    private Long userId;
    private Boolean isHost;
    private Integer score;
    private String team;

    public GameUserGetResponseDto(GameUserEntity entity) {
        this.code = entity.getCode();
        this.gameCode = entity.getGameCode();
        this.userId = entity.getUserId();
        this.isHost = entity.getIsHost();
        this.score = entity.getScore();
        this.team = entity.getTeam();
    }
}
