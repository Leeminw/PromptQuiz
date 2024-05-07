package com.ssafy.apm.gameuser.dto.response;

import com.ssafy.apm.gameuser.domain.GameUser;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameUserResponseDto {
    private String code;
    private String gameCode;
    private Long userId;
    private String team;
    private Integer score;
    private Boolean isHost;

    public GameUserResponseDto(GameUser entity) {
        this.code = entity.getCode();
        this.gameCode = entity.getGameCode();
        this.userId = entity.getUserId();
        this.team = entity.getTeam();
        this.score = entity.getScore();
        this.isHost = entity.getIsHost();
    }

}
