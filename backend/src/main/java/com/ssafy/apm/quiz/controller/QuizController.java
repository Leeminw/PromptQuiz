package com.ssafy.apm.quiz.controller;

import com.ssafy.apm.quiz.service.QuizService;
import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.quiz.dto.response.QuizDetailResponseDto;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quiz")
public class QuizController {
    private final QuizService service;

    // 퀴즈 상세 정보 조회
    @GetMapping("/{quiz-id}")
    public ResponseEntity<?> getQuizDetail(@PathVariable("quiz-id") Long quizId) {
        QuizDetailResponseDto response = service.getQuizInfo(quizId);
        return sendResponse(response, HttpStatus.OK);
    }

    // response 객체 생성 메서드
    public ResponseEntity<?> sendResponse(Object response, HttpStatus status) {
        return ResponseEntity.status(status).body(ResponseData.success(response));
    }
}
