package com.ssafy.apm.quiz.domain;

import com.ssafy.apm.quiz.dto.request.QuizRequestDto;

import lombok.*;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
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
    private String engSubAdjective;
    private String engObjAdjective;

    private String korSentence;
    private String korSubject;
    private String korObject;
    private String korVerb;
    private String korSubAdjective;
    private String korObjAdjective;

    @CreationTimestamp
    private LocalDateTime createdDate;
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    public Quiz update(QuizRequestDto requestDto) {
        if (requestDto.getUrl() != null) this.url = requestDto.getUrl();
        if (requestDto.getStyle() != null) this.style = requestDto.getStyle();
        if (requestDto.getGroupCode() != null) this.groupCode = requestDto.getGroupCode();

        if (requestDto.getEngVerb() != null) this.engVerb = requestDto.getEngVerb();
        if (requestDto.getEngObject() != null) this.engObject = requestDto.getEngObject();
        if (requestDto.getEngSubject() != null) this.engSubject = requestDto.getEngSubject();
        if (requestDto.getEngSentence() != null) this.engSentence = requestDto.getEngSentence();
        if (requestDto.getEngSubAdjective() != null) this.engSubAdjective = requestDto.getEngSubAdjective();
        if (requestDto.getEngObjAdjective() != null) this.engObjAdjective = requestDto.getEngObjAdjective();

        if (requestDto.getKorVerb() != null) this.korVerb = requestDto.getKorVerb();
        if (requestDto.getKorObject() != null) this.korObject = requestDto.getKorObject();
        if (requestDto.getKorSubject() != null) this.korSubject = requestDto.getKorSubject();
        if (requestDto.getKorSentence() != null) this.korSentence = requestDto.getKorSentence();
        if (requestDto.getKorSubAdjective() != null) this.korSubAdjective = requestDto.getKorSubAdjective();
        if (requestDto.getKorObjAdjective() != null) this.korObjAdjective = requestDto.getKorObjAdjective();

        return this;
    }

}
