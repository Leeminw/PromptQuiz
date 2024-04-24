package com.ssafy.apm.quiz.dto.response;

import com.ssafy.apm.quiz.domain.Quiz;
import lombok.Data;

@Data
public class QuizDetailResponseDto {
    private Long id;
    private Long promptGroup;
    private String image;
    private String prompt;
    private Integer style;

    public QuizDetailResponseDto(Quiz entity){
        this.id = entity.getId();
        this.promptGroup = entity.getPromptGroup();
        this.image = entity.getImage();
        this.prompt = entity.getPrompt();
        this.style = entity.getStyle();
    }
}
