package com.ssafy.apm.gamequiz.dto.response;

import com.ssafy.apm.gamequiz.domain.GameQuizEntity;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameQuizGetResponseDto {
    private String code;
    private String gameCode;
    private Long quizId;
    private Integer type;
    private Integer round;
    private Boolean isAnswer;

    public GameQuizGetResponseDto(GameQuizEntity entity) {
        this.code = entity.getCode();
        this.gameCode = entity.getGameCode();
        this.quizId = entity.getQuizId();
        this.type = entity.getType();
        this.round = entity.getRound();
        this.isAnswer = entity.getIsAnswer();
    }
}
