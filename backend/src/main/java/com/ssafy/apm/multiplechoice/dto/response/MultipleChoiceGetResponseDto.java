package com.ssafy.apm.multiplechoice.dto.response;

import com.ssafy.apm.multiplechoice.domain.MultipleChoiceEntity;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MultipleChoiceGetResponseDto {

    private Long id;
    private Long gameQuizId;
    private Long quizId;

    public MultipleChoiceGetResponseDto(MultipleChoiceEntity entity){
        this.id = entity.getId();
        this.gameQuizId = entity.getGameQuizId();
        this.quizId = entity.getQuizId();
    }

}
