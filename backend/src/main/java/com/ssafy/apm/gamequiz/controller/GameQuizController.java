package com.ssafy.apm.gamequiz.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.response.GameGetResponseDto;
import com.ssafy.apm.gamequiz.dto.response.GameQuizGetResponseDto;
import com.ssafy.apm.gamequiz.service.GameQuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/game-quiz")
@RequiredArgsConstructor
public class GameQuizController {

    private final GameQuizService gameQuizService;

    @GetMapping("/{gameId}")
    public ResponseEntity<ResponseData<?>> getGameQuizDetail(@PathVariable Long gameId) {
        GameQuizGetResponseDto response = gameQuizService.getGameQuizDetail(gameId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(response));
    }

    @GetMapping("/createQuiz/{gameId}")
    public ResponseEntity<ResponseData<?>> createAnswerGameQuiz(@PathVariable Long gameId) {
        Boolean response = gameQuizService.createAnswerGameQuiz(gameId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(response));
    }

    @DeleteMapping("/deleteQuiz/{gameId}")
    public ResponseEntity<ResponseData<?>> deleteGameQuiz(@PathVariable Long gameId) {
        Long response = gameQuizService.deleteGameQuiz(gameId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(response));
    }
}
