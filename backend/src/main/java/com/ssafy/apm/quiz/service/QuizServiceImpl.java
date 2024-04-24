package com.ssafy.apm.quiz.service;

import com.ssafy.apm.quiz.domain.QuizEntity;
import com.ssafy.apm.quiz.dto.request.QuizGetRequestDto;
import com.ssafy.apm.quiz.dto.response.QuizDetailResponseDto;
import com.ssafy.apm.quiz.exception.QuizNotFoundException;
import com.ssafy.apm.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService{
    private final QuizRepository repository;

    @Override
    public QuizDetailResponseDto getQuizInfo(QuizGetRequestDto request) {
        QuizEntity entity = repository.findById(request.getQuizId()).orElseThrow(() -> new QuizNotFoundException(request.getQuizId()));
        return new QuizDetailResponseDto(entity);
    }
}
