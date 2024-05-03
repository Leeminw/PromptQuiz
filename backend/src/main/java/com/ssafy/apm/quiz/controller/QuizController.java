package com.ssafy.apm.quiz.controller;

import com.ssafy.apm.prompt.dto.PromptRequestDto;
import com.ssafy.apm.prompt.dto.PromptResponseDto;
import com.ssafy.apm.quiz.dto.request.QuizRequestDto;
import com.ssafy.apm.quiz.service.QuizService;
import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.quiz.dto.response.QuizDetailResponseDto;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService service;

    @PostMapping("")
    public ResponseEntity<ResponseData<?>> createQuiz(@RequestBody QuizRequestDto requestDto) {
        QuizDetailResponseDto responseDto = service.createQuiz(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @PutMapping("")
    public ResponseEntity<ResponseData<?>> updateQuiz(@RequestBody QuizRequestDto requestDto) {
        QuizDetailResponseDto responseDto = service.updateQuiz(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<?>> deleteQuiz(@PathVariable Long id) {
        QuizDetailResponseDto responseDto = service.deleteQuiz(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<?>> findQuizById(@PathVariable Long id) {
        QuizDetailResponseDto responseDto = service.findQuizById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @GetMapping("")
    public ResponseEntity<ResponseData<?>> findAllQuizs() {
        List<QuizDetailResponseDto> responseDto = service.findAllQuizs();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/search")
    public ResponseEntity<ResponseData<?>> searchQuizs(
            @RequestParam(value = "style", required = false) String style,
            @RequestParam(value = "groupCode", required = false) String groupCode) {
        List<QuizDetailResponseDto> responseDto = List.of();
        if (style != null) responseDto = service.filterQuizByStyle(style);
        else if (groupCode != null) responseDto = service.filterQuizsByGroupCode(groupCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @GetMapping("/random")
    public ResponseEntity<ResponseData<?>> randomQuizs(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "groupCode", required = false) String groupCode) {
        if (groupCode == null && limit == null) {
            QuizDetailResponseDto responseDto = service.extractRandomQuiz();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
        }
        List<QuizDetailResponseDto> responseDto = List.of();
        if (groupCode == null && limit != null)
            responseDto = service.extractRandomQuizs(limit);
        else if (groupCode != null && limit == null)
            responseDto = service.extractRandomQuizsByGroupCode(groupCode, 5);
        else if (groupCode != null && limit != null)
            responseDto = service.extractRandomQuizsByGroupCode(groupCode, limit);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    // response 객체 생성 메서드
    public ResponseEntity<?> sendResponse(Object response, HttpStatus status) {
        return ResponseEntity.status(status).body(ResponseData.success(response));
    }
}
