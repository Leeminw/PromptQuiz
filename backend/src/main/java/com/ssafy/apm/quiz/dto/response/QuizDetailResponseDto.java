package com.ssafy.apm.quiz.dto.response;

import com.ssafy.apm.prompt.domain.Prompt;
import com.ssafy.apm.quiz.domain.Quiz;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuizDetailResponseDto {
    private Long id;
    private String url;
    private String style;
    private String groupCode;

    private String engSentence;
    private String engSubject;
    private String engObject;
    private String engVerb;
    private String engAdverb;
    private String engAdjective;

    private String korSentence;
    private String korSubject;
    private String korObject;
    private String korVerb;
    private String korAdverb;
    private String korAdjective;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public QuizDetailResponseDto(Quiz entity) {
        this.id = entity.getId();
        this.url = entity.getUrl();
        this.style = entity.getStyle();
        this.groupCode = entity.getGroupCode();

        this.engAdjective = entity.getEngAdjective();
        this.engSentence = entity.getEngSentence();
        this.engSubject = entity.getEngSubject();
        this.engObject = entity.getEngObject();
        this.engAdverb = entity.getEngAdverb();
        this.engVerb = entity.getEngVerb();

        this.korAdjective = entity.getKorAdjective();
        this.korSentence = entity.getKorSentence();
        this.korSubject = entity.getKorSubject();
        this.korObject = entity.getKorObject();
        this.korAdverb = entity.getKorAdverb();
        this.korVerb = entity.getKorVerb();

        this.createdDate = entity.getCreatedDate();
        this.updatedDate = entity.getUpdatedDate();
    }
}
