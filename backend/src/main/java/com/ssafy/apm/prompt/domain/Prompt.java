package com.ssafy.apm.prompt.domain;

import com.ssafy.apm.prompt.dto.PromptRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prompt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @CreationTimestamp
    private LocalDateTime createdDate;
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    public Prompt update(PromptRequestDto requestDto) {
        if (requestDto.getUrl() != null) this.url = requestDto.getUrl();
        if (requestDto.getStyle() != null) this.style = requestDto.getStyle();
        if (requestDto.getGroupCode() != null) this.groupCode = requestDto.getGroupCode();

        if (requestDto.getEngAdjective() != null) this.engAdjective = requestDto.getEngAdjective();
        if (requestDto.getEngSentence() != null) this.engSentence = requestDto.getEngSentence();
        if (requestDto.getEngSubject() != null) this.engSubject = requestDto.getEngSubject();
        if (requestDto.getEngObject() != null) this.engObject = requestDto.getEngObject();
        if (requestDto.getEngAdverb() != null) this.engAdverb = requestDto.getEngAdverb();
        if (requestDto.getEngVerb() != null) this.engVerb = requestDto.getEngVerb();

        if (requestDto.getKorAdjective() != null) this.korAdjective = requestDto.getKorAdjective();
        if (requestDto.getKorSentence() != null) this.korSentence = requestDto.getKorSentence();
        if (requestDto.getKorSubject() != null) this.korSubject = requestDto.getKorSubject();
        if (requestDto.getKorObject() != null) this.korObject = requestDto.getKorObject();
        if (requestDto.getKorAdverb() != null) this.korAdverb = requestDto.getKorAdverb();
        if (requestDto.getKorVerb() != null) this.korVerb = requestDto.getKorVerb();

        return this;
    }

    public Prompt updateUrl(String url) {
        this.url = url;
        return this;
    }

}
