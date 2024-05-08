package com.ssafy.apm.game.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import com.ssafy.apm.game.dto.response.GameResponseDto;
import com.ssafy.apm.game.service.GameService;
import com.ssafy.apm.gameuser.dto.response.GameUserSimpleResponseDto;
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

    @GetMapping("/createQuiz/{gameCode}")
    public ResponseEntity<ResponseData<?>> createAnswerGameQuiz(@PathVariable(name = "gameCode") String gameCode) {
        Boolean response = gameService.createGameQuiz(gameCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(response));
    }

    //    방 생성
    @PostMapping("")
    public ResponseEntity<ResponseData<?>> createGame(@RequestBody GameCreateRequestDto requestDto) {
        GameResponseDto response = gameService.createGame(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success("방 생성 완료", response));
    }
    //    // 게임 입장할 때 GameUserEntity 생성
    @PostMapping("/enterGame/{gameCode}")
    public ResponseEntity<ResponseData<?>> enterGame(@PathVariable(name = "gameCode") String gameCode) {
        GameUserSimpleResponseDto response = gameService.enterGame(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }

    //    방 목록들 조회
    @GetMapping("/gameList/{channelCode}")
    public ResponseEntity<ResponseData<?>> findGamesByChannelCode(@PathVariable(name = "channelCode") String channelCode) {
        List<GameResponseDto> response = gameService.findGamesByChannelCode(channelCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방 목록 조회 완료", response));
    }

    //    방, 채널 상세 정보 조회
    @GetMapping("{gameCode}")
    public ResponseEntity<ResponseData<?>> findGameByGameCode(@PathVariable(name = "gameCode") String gameCode) {
        GameResponseDto response = gameService.findGameByGameCode(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방, 채널 상세 정보 조회 완료", response));
    }

    //    방 정보 수정 API
    @PutMapping("")
    public ResponseEntity<ResponseData<?>> updateGame(@RequestBody GameUpdateRequestDto requestDto) {
        GameResponseDto response = gameService.updateGame(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방 설정 수정 완료", response));
    }

    @DeleteMapping("/{gameCode}")
    public ResponseEntity<ResponseData<?>> deleteGame(@PathVariable(name = "gameCode") String gameCode) {
        GameResponseDto response = gameService.deleteGame(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방 삭제 완료", response));
    }
    /* FIXME: GameController 로 이동하는 것이 나아보임 */
    /* FIXME 1: 퇴장 조건을 GameService 에서 판단하고,
     *   GameService 에서 deleteGameUser() 를 호출하는 것이 나아보임 */
    /* FIXME 2: 혹은 단순 deleteGameUser API 호출을 통해 처리하고,
     *   WebSocket 통신이 끊어지는 경우에도, deleteGameUser()를 호출하는 것이 나아보임*/
//    // 게임 퇴장할 때 GameUserEntity 삭제
    @DeleteMapping("/exitGame/{gameCode}")
    public ResponseEntity<ResponseData<?>> exitGame(@PathVariable(name = "gameCode") String gameCode) {
//        삭제된 gameUserId 리턴
        String response = gameService.exitGame(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }
}
