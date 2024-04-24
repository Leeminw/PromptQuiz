package com.ssafy.apm.quiz.service;

import com.ssafy.apm.common.dto.request.GameAnswerRequestDto;
import com.ssafy.apm.quiz.dto.response.QuizDetailResponseDto;

public interface QuizService {
    QuizDetailResponseDto getQuizInfo(Long quizId);

    Boolean answerQuizCheck(GameAnswerRequestDto answer);

    Boolean answerOrderCheck(GameAnswerRequestDto answer);

    Double answerSimilarityCheck(GameAnswerRequestDto answer);
}
