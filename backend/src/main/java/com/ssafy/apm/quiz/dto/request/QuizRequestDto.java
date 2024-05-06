package com.ssafy.apm.quiz.dto.request;

import com.ssafy.apm.quiz.domain.Quiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequestDto {
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

    public Quiz toEntity() {
        return Quiz.builder()
                .id(this.id)
                .url(this.url)
                .style(this.style)
                .groupCode(this.groupCode)
                .engVerb(this.engVerb)
                .engObject(this.engObject)
                .engSubject(this.engSubject)
                .engSentence(this.engSentence)
                .engSubAdjective(this.engSubAdjective)
                .engObjAdjective(this.engObjAdjective)
                .korVerb(this.korVerb)
                .korObject(this.korObject)
                .korSubject(this.korSubject)
                .korSentence(this.korSentence)
                .korSubAdjective(this.korSubAdjective)
                .korObjAdjective(this.korObjAdjective)
                .build();
    }

}
