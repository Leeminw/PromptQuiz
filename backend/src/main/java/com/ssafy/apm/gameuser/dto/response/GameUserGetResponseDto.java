package com.ssafy.apm.gameuser.dto.response;

import com.ssafy.apm.gameuser.domain.GameUserEntity;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameUserGetResponseDto {
    private Long id;
    private Long gameId;
    private Long userId;
    private Boolean isHost;
    private Boolean isReady;
    private Integer score;
    private String team;
    private Integer ranking;

    public GameUserGetResponseDto(GameUserEntity entity) {
        this.id = entity.getId();
        this.gameId = entity.getGameId();
        this.userId = entity.getUserId();
        this.isHost = entity.getIsHost();
        this.isReady = entity.getIsReady();
        this.score = entity.getScore();
        this.team = entity.getTeam();
        this.ranking = entity.getRanking();
    }
}
