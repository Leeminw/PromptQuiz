package com.ssafy.apm.gamequiz.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.gamequiz.dto.response.GameQuizDetailResponseDto;
import com.ssafy.apm.gamequiz.dto.response.GameQuizSimpleResponseDto;
import com.ssafy.apm.gamequiz.service.GameQuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/game-quiz")
public class GameQuizController {

    private final GameQuizService gameQuizService;

    @DeleteMapping("/{gameCode}")
    public ResponseEntity<ResponseData<?>> deleteGameQuiz(@PathVariable String gameCode) {
        GameQuizSimpleResponseDto responseDto = gameQuizService.deleteGameQuiz(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/search-current/{gameCode}")
    public ResponseEntity<ResponseData<?>> findCurrentDetailGameQuizzesByGameCode(@PathVariable String gameCode) {
        List<GameQuizDetailResponseDto> responseDto = gameQuizService.findCurrentDetailGameQuizzesByGameCode(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @GetMapping("/search-all/{gameCode}")
    public ResponseEntity<ResponseData<?>> findDetailGameQuizzesByGameCode(@PathVariable String gameCode) {
        List<GameQuizDetailResponseDto> responseDto = gameQuizService.findDetailGameQuizzesByGameCode(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

}
