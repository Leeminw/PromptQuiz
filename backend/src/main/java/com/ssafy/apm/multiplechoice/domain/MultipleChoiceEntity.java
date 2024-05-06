package com.ssafy.apm.multiplechoice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "multiple-choice", timeToLive = 3600)
public class MultipleChoiceEntity {
    @Id
    private Long id;
    @Indexed
    private Long gameQuizId;
    @Indexed
    private Long quizId;

}
