package com.ssafy.apm.gamequiz.domain;

import com.ssafy.apm.gamequiz.dto.request.GameQuizUpdateRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String code;
    private String gameCode;
    private Long quizId;
    private Integer type;
    private Integer round;
    private Integer number;
    private Boolean isAnswer;

    public GameQuiz update(GameQuizUpdateRequestDto requestDto) {
        if (requestDto.getGameCode() != null) this.gameCode = requestDto.getGameCode();
        if (requestDto.getQuizId() != null) this.quizId = requestDto.getQuizId();
        if (requestDto.getType() != null) this.type = requestDto.getType();
        if (requestDto.getRound() != null) this.round = requestDto.getRound();
        if (requestDto.getIsAnswer() != null) this.isAnswer = requestDto.getIsAnswer();
        return this;
    }

}
