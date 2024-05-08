package com.ssafy.apm.gamequiz.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "game-quiz", timeToLive = 3600)
public class GameQuizEntity {
    @Id
    private String code;
    @Indexed
    private String gameCode;
    @Indexed
    private Long quizId;
    //    객관식, 빈칸객관식, 빈칸주관식
    private Integer type;
    @Indexed
    private Integer round;
    @Indexed
    private Boolean isAnswer;

}
