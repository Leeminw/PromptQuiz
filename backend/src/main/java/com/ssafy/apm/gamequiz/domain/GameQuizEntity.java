package com.ssafy.apm.gamequiz.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "game-quiz")
public class GameQuizEntity {
    @Id
    private Long id;
    private Long gameId;
    private Long quizId;
    private Integer type;

}
