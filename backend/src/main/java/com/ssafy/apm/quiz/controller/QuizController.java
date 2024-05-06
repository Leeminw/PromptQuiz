package com.ssafy.apm.quiz.controller;

import com.ssafy.apm.quiz.dto.request.QuizRequestDto;
import com.ssafy.apm.quiz.service.QuizService;
import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.quiz.dto.response.QuizResponseDto;

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

    private final QuizService quizService;

    @PostMapping("")
    public ResponseEntity<ResponseData<?>> createQuiz(@RequestBody QuizRequestDto requestDto) {
        QuizResponseDto responseDto = quizService.createQuiz(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @PutMapping("")
    public ResponseEntity<ResponseData<?>> updateQuiz(@RequestBody QuizRequestDto requestDto) {
        QuizResponseDto responseDto = quizService.updateQuiz(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<?>> deleteQuiz(@PathVariable Long id) {
        QuizResponseDto responseDto = quizService.deleteQuiz(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<?>> findQuizById(@PathVariable Long id) {
        QuizResponseDto responseDto = quizService.findQuizById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @GetMapping("")
    public ResponseEntity<ResponseData<?>> findAllQuizzes() {
        List<QuizResponseDto> responseDto = quizService.findAllQuizzes();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/search")
    public ResponseEntity<ResponseData<?>> searchQuizzes(
            @RequestParam(value = "style", required = false) String style,
            @RequestParam(value = "groupCode", required = false) String groupCode) {
        List<QuizResponseDto> responseDto = List.of();
        if (style != null) responseDto = quizService.filterQuizByStyle(style);
        else if (groupCode != null) responseDto = quizService.filterQuizzesByGroupCode(groupCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @GetMapping("/random")
    public ResponseEntity<ResponseData<?>> randomQuizzes(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "groupCode", required = false) String groupCode) {
        if (groupCode == null && limit == null) {
            QuizResponseDto responseDto = quizService.extractRandomQuiz();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
        }
        List<QuizResponseDto> responseDto = List.of();
        if (groupCode == null && limit != null)
            responseDto = quizService.extractRandomQuizzes(limit);
        else if (groupCode != null && limit == null)
            responseDto = quizService.extractRandomQuizzesByGroupCode(groupCode, 5);
        else if (groupCode != null && limit != null)
            responseDto = quizService.extractRandomQuizzesByGroupCode(groupCode, limit);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // response 객체 생성 메서드
    public ResponseEntity<?> sendResponse(Object response, HttpStatus status) {
        return ResponseEntity.status(status).body(ResponseData.success(response));
    }

}
