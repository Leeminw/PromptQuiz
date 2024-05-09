package com.ssafy.apm.gamequiz.dto.response;

import com.ssafy.apm.gamequiz.domain.GameQuiz;
import com.ssafy.apm.quiz.domain.Quiz;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameQuizDetailResponseDto {
    private String code;
    private String gameCode;
    private Long quizId;
    private Integer type;
    private Integer round;
    private Integer number;
    private Boolean isAnswer;

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

    public GameQuizDetailResponseDto(GameQuiz gameQuiz, Quiz quiz) {
        this.code = gameQuiz.getCode();
        this.gameCode = gameQuiz.getGameCode();
        this.quizId = gameQuiz.getQuizId();
        this.type = gameQuiz.getType();
        this.round = gameQuiz.getRound();
        this.number = gameQuiz.getNumber();
        this.isAnswer = gameQuiz.getIsAnswer();

        this.url = quiz.getUrl();
        this.style = quiz.getStyle();
        this.groupCode = quiz.getGroupCode();

        this.engVerb = quiz.getEngVerb();
        this.engObject = quiz.getEngObject();
        this.engSubject = quiz.getEngSubject();
        this.engSentence = quiz.getEngSentence();
        this.engSubAdjective = quiz.getEngSubAdjective();
        this.engObjAdjective = quiz.getEngObjAdjective();

        this.korVerb = quiz.getKorVerb();
        this.korObject = quiz.getKorObject();
        this.korSubject = quiz.getKorSubject();
        this.korSentence = quiz.getKorSentence();
        this.korSubAdjective = quiz.getKorSubAdjective();
        this.korObjAdjective = quiz.getKorObjAdjective();
    }

}
