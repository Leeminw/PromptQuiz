package com.ssafy.apm.quiz.dto.response;

import com.ssafy.apm.quiz.domain.Quiz;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponseDto {
    private Long id;
    private String url;
    private String style;
    private String groupCode;

    private String engVerb;
    private String engObject;
    private String engSubject;
    private String engSentence;
    private String engSubAdjective;
    private String engObjAdjective;

    private String korVerb;
    private String korObject;
    private String korSubject;
    private String korSentence;
    private String korSubAdjective;
    private String korObjAdjective;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public QuizResponseDto(Quiz entity) {
        this.id = entity.getId();
        this.url = entity.getUrl();
        this.style = entity.getStyle();
        this.groupCode = entity.getGroupCode();

        this.engVerb = entity.getEngVerb();
        this.engObject = entity.getEngObject();
        this.engSubject = entity.getEngSubject();
        this.engSentence = entity.getEngSentence();
        this.engSubAdjective = entity.getEngSubAdjective();
        this.engObjAdjective = entity.getEngObjAdjective();

        this.korVerb = entity.getKorVerb();
        this.korObject = entity.getKorObject();
        this.korSubject = entity.getKorSubject();
        this.korSentence = entity.getKorSentence();
        this.korSubAdjective = entity.getKorSubAdjective();
        this.korObjAdjective = entity.getKorObjAdjective();

        this.createdDate = entity.getCreatedDate();
        this.updatedDate = entity.getUpdatedDate();
    }

}
