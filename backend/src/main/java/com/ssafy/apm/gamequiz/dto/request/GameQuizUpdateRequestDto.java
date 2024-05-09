package com.ssafy.apm.gamequiz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameQuizUpdateRequestDto {
    private String code;
    private String gameCode;
    private Long quizId;
    private Integer type;
    private Integer round;
    private Integer number;
    private Boolean isAnswer;
}
