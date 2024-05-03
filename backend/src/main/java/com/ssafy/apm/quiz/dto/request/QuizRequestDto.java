package com.ssafy.apm.quiz.dto.request;

import com.ssafy.apm.prompt.domain.Prompt;
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

    public Quiz toEntity() {
        return Quiz.builder()
                .id(this.id)
                .url(this.url)
                .style(this.style)
                .groupCode(this.groupCode)
                .engSentence(this.engSentence)
                .engSubject(this.engSubject)
                .engObject(this.engObject)
                .engVerb(this.engVerb)
                .engAdverb(this.engAdverb)
                .engAdjective(this.engAdjective)
                .korSentence(this.korSentence)
                .korSubject(this.korSubject)
                .korObject(this.korObject)
                .korVerb(this.korVerb)
                .korAdverb(this.korAdverb)
                .korAdjective(this.korAdjective)
                .build();
    }
}
