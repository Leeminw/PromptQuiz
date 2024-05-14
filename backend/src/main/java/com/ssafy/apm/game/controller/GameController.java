package com.ssafy.apm.game.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import com.ssafy.apm.game.dto.response.GameResponseDto;
import com.ssafy.apm.game.service.GameService;
import com.ssafy.apm.gameuser.dto.response.GameUserSimpleResponseDto;
import com.ssafy.apm.quiz.dto.request.QuizRequestDto;
import com.ssafy.apm.quiz.dto.response.QuizResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    //    방 생성
    @PostMapping("")
    public ResponseEntity<ResponseData<?>> createGame(@RequestBody GameCreateRequestDto requestDto) {
        GameResponseDto response = gameService.createGame(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success("방 생성 완료", response));
    }

    //    게임 입장
    @PostMapping("/enterGame/{gameCode}")
    public ResponseEntity<ResponseData<?>> enterGame(@PathVariable(name = "gameCode") String gameCode) {
        GameUserSimpleResponseDto response = gameService.enterGame(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }

    //    채널 내 방 목록
    @GetMapping("/gameList/{channelCode}")
    public ResponseEntity<ResponseData<?>> findGamesByChannelCode(@PathVariable(name = "channelCode") String channelCode) {
        List<GameResponseDto> response = gameService.findGamesByChannelCode(channelCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방 목록 조회 완료", response));
    }

    //    방 조회
    @GetMapping("{gameCode}")
    public ResponseEntity<ResponseData<?>> findGameByGameCode(@PathVariable(name = "gameCode") String gameCode) {
        GameResponseDto response = gameService.findGameByGameCode(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방, 채널 상세 정보 조회 완료", response));
    }

    @GetMapping("/createQuiz/{gameCode}")
    public ResponseEntity<ResponseData<?>> createAnswerGameQuiz(@PathVariable(name = "gameCode") String gameCode) {
        Boolean response = gameService.createGameQuiz(gameCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(response));
    }

    @GetMapping("/showCreateQuiz/{gameCode}")// Redis에 저장 안하고 그냥 잘되는지 확인용
    public ResponseEntity<ResponseData<?>> createAnswerGameQuizCanShow(@PathVariable(name = "gameCode") String gameCode) {
        List<QuizResponseDto> response = gameService.createAnswerGameQuizCanShow(gameCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(response));
    }

    @PutMapping("")
    public ResponseEntity<ResponseData<?>> updateGame(@RequestBody GameUpdateRequestDto requestDto) {
        GameResponseDto response = gameService.updateGame(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방 설정 수정 완료", response));
    }

    @PutMapping("/reset")
    public ResponseEntity<ResponseData<?>> resetGame(@RequestParam String gameCode) {
        GameResponseDto response = gameService.resetGame(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("게임 종료시 방 데이터 초기화", response));
    }

    @DeleteMapping("/{gameCode}")
    public ResponseEntity<ResponseData<?>> deleteGame(@PathVariable(name = "gameCode") String gameCode) {
        GameResponseDto response = gameService.deleteGame(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방 삭제 완료", response));
    }

    //    방 퇴장
    @DeleteMapping("/exitGame/{gameCode}")
    public ResponseEntity<ResponseData<?>> exitGame(@PathVariable(name = "gameCode") String gameCode) {
        String response = gameService.exitGame(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }
}
