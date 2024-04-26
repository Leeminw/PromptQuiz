package com.ssafy.apm.gamequiz.dto.response;

import com.ssafy.apm.gamequiz.domain.GameQuizEntity;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameQuizGetResponseDto {
    private Long id;
    private Long gameId;
    private Long quizId;
    private Integer type;
    private Integer round;

    public GameQuizGetResponseDto(GameQuizEntity entity) {
        this.id = entity.getId();
        this.gameId = entity.getGameId();
        this.quizId = entity.getQuizId();
        this.type = entity.getType();
        this.round = entity.getRound();
    }
}
