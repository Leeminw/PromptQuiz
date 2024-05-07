package com.ssafy.apm.quiz.controller;

import com.ssafy.apm.quiz.service.QuizService;
import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.quiz.dto.request.QuizRequestDto;
import com.ssafy.apm.quiz.dto.response.QuizResponseDto;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

    @PostMapping("")
    public ResponseEntity<?> createQuiz(@RequestBody QuizRequestDto requestDto) {
        QuizResponseDto responseDto = quizService.createQuiz(requestDto);
        return sendResponse(HttpStatus.OK, responseDto);
    }

    @PutMapping("")
    public ResponseEntity<?> updateQuiz(@RequestBody QuizRequestDto requestDto) {
        QuizResponseDto responseDto = quizService.updateQuiz(requestDto);
        return sendResponse(HttpStatus.OK, responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {
        QuizResponseDto responseDto = quizService.deleteQuiz(id);
        return sendResponse(HttpStatus.OK, responseDto);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/{id}")
    public ResponseEntity<?> findQuizById(@PathVariable Long id) {
        QuizResponseDto responseDto = quizService.findQuizById(id);
        return sendResponse(HttpStatus.OK, responseDto);
    }

    @GetMapping("")
    public ResponseEntity<?> findAllQuizzes() {
        List<QuizResponseDto> responseDto = quizService.findAllQuizzes();
        return sendResponse(HttpStatus.OK, responseDto);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/search")
    public ResponseEntity<?> searchQuizzes(
            @RequestParam(value = "style", required = false) String style,
            @RequestParam(value = "groupCode", required = false) String groupCode) {

        List<QuizResponseDto> responseDto = List.of();
        if (style != null) responseDto = quizService.filterQuizByStyle(style);
        else if (groupCode != null) responseDto = quizService.filterQuizzesByGroupCode(groupCode);

        return sendResponse(HttpStatus.OK, responseDto);
    }

    @GetMapping("/random")
    public ResponseEntity<?> randomQuizzes(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "groupCode", required = false) String groupCode) {

        if (groupCode == null && limit == null) {
            QuizResponseDto responseDto = quizService.extractRandomQuiz();
            return sendResponse(HttpStatus.OK, responseDto);
        }

        List<QuizResponseDto> responseDto;
        if (groupCode == null) responseDto = quizService.extractRandomQuizzes(limit);
        else responseDto = quizService.extractRandomQuizzesByGroupCode(groupCode, Objects.requireNonNullElse(limit, 5));

        return sendResponse(HttpStatus.OK, responseDto);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ResponseEntity<?> sendResponse(HttpStatus status, Object response) {
        return ResponseEntity.status(status).body(ResponseData.success(response));
    }

}
