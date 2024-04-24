package com.ssafy.apm.quiz.service;

import com.ssafy.apm.quiz.domain.Quiz;
import com.ssafy.apm.quiz.repository.QuizRepository;
import com.ssafy.apm.quiz.exception.QuizNotFoundException;
import com.ssafy.apm.quiz.dto.response.QuizDetailResponseDto;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService{
    private final QuizRepository repository;

    @Override
    public QuizDetailResponseDto getQuizInfo(Long quizId) {
        Quiz entity = repository.findById(quizId).orElseThrow(() -> new QuizNotFoundException(quizId));
        return new QuizDetailResponseDto(entity);
    }

    // 라벤슈타인 문자열 유사도 측정 코드
    public double calcSimilarity(String input, String answer) {
        // 공백 제거하기
        input = input.replace(" ","");
        answer = answer.replace(" ", "");
        
        LevenshteinDistance ld = new LevenshteinDistance();
        int maxLen = Math.max(input.length(), answer.length());

        double result = 0;
        double temp = ld.apply(input, answer);
        result = (maxLen - temp) / maxLen;

        return result;
    }
}
