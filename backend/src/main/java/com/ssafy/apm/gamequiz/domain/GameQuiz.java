package com.ssafy.apm.gamequiz.domain;

import com.ssafy.apm.gamequiz.dto.request.GameQuizUpdateRequestDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(timeToLive = 3600)
public class GameQuiz {
    @Id
    private String code;
    @Indexed
    private String gameCode;
    private Long quizId;
    /* 객관식, 빈칸 객관식, 빈칸 주관식 */
    private Integer type;
    @Indexed
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
