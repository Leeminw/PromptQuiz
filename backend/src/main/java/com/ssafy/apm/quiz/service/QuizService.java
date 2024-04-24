package com.ssafy.apm.quiz.service;

import com.ssafy.apm.quiz.dto.response.QuizDetailResponseDto;

public interface QuizService {
    QuizDetailResponseDto getQuizInfo(Long quizId);
}
