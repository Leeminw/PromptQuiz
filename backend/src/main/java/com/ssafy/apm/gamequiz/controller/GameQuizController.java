package com.ssafy.apm.gamequiz.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.gamequiz.dto.response.GameQuizGetResponseDto;
import com.ssafy.apm.gamequiz.service.GameQuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/game-quiz")
@RequiredArgsConstructor
public class GameQuizController {

    private final GameQuizService gameQuizService;

    @GetMapping("/{gameCode}")
    public ResponseEntity<ResponseData<?>> getGameQuizListEachRoundByGameCode(@PathVariable(name = "gameCode") String gameCode) {
        List<GameQuizGetResponseDto> response = gameQuizService.getGameQuizListEachRoundByGameCode(gameCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(response));
    }
    @GetMapping("/search/{gameCode}")
    public ResponseEntity<ResponseData<?>> getAllGameQuizListByGameCode(@PathVariable(name = "gameCode") String gameCode) {
        List<GameQuizGetResponseDto> response = gameQuizService.getAllGameQuizListByGameCode(gameCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(response));
    }

    @DeleteMapping("/{gameCode}")
    public ResponseEntity<ResponseData<?>> deleteGameQuiz(@PathVariable(name = "gameCode") String gameCode) {
        String response = gameQuizService.deleteGameQuiz(gameCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(response));
    }
}
