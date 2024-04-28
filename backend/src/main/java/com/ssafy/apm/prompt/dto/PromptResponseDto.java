package com.ssafy.apm.prompt.dto;

import com.ssafy.apm.prompt.domain.Prompt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptResponseDto {
    private Long id;
    private String url;
    private String style;
    private Integer group;

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

    public PromptResponseDto(Prompt entity) {
        this.id = entity.getId();
        this.url = entity.getUrl();
        this.group = entity.getGroup();
        this.style = entity.getStyle();

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
