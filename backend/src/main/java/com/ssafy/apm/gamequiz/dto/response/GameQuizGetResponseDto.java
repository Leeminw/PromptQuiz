package com.ssafy.apm.gamequiz.dto.response;

import com.ssafy.apm.gamequiz.domain.GameQuizEntity;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameQuizGetResponseDto {
    private String id;
    private String gameId;
    private Long quizId;
    private Integer type;
    private Integer round;

    public GameQuizGetResponseDto(GameQuizEntity entity) {
        this.id = entity.getId().toString();
        this.gameId = entity.getGameId().toString();
        this.quizId = entity.getQuizId();
        this.type = entity.getType();
        this.round = entity.getRound();
    }
}
