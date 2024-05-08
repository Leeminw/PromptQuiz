package com.ssafy.apm.gamequiz.dto.request;

import com.ssafy.apm.gamequiz.domain.GameQuiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameQuizCreateRequestDto {
    private String gameCode;
    private Long quizId;
    private Integer type;
    private Integer round;
    private Boolean isAnswer;

    public GameQuiz toEntity() {
        return GameQuiz.builder()
                .gameCode(this.gameCode)
                .quizId(this.quizId)
                .type(this.type)
                .round(this.round)
                .isAnswer(this.isAnswer)
                .build();
    }

}
