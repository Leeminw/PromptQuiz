package com.ssafy.apm.gameuser.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.gameuser.dto.request.GameUserCreateRequestDto;
import com.ssafy.apm.gameuser.dto.request.GameUserUpdateRequestDto;
import com.ssafy.apm.gameuser.dto.response.GameUserDetailResponseDto;
import com.ssafy.apm.gameuser.dto.response.GameUserSimpleResponseDto;
import com.ssafy.apm.gameuser.service.GameUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/game-user")
public class GameUserController {

    private final GameUserService gameUserService;

    @PostMapping
    public ResponseEntity<ResponseData<?>> createGameUser(@RequestBody GameUserCreateRequestDto requestDto) {
        GameUserSimpleResponseDto responseDto = gameUserService.createGameUser(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @PutMapping
    public ResponseEntity<ResponseData<?>> updateGameUser(@RequestBody GameUserUpdateRequestDto requestDto) {
        GameUserSimpleResponseDto responseDto = gameUserService.updateGameUser(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @DeleteMapping
    public ResponseEntity<ResponseData<?>> deleteGameUser(@RequestParam(required = false) String code,
                                                          @RequestParam(required = false) String gameCode,
                                                          @RequestParam(required = false) Long userId) {
        if (code != null) {
            GameUserSimpleResponseDto responseDto = gameUserService.deleteGameUser(code);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
        } else if (gameCode != null && userId != null) {
            GameUserSimpleResponseDto responseDto = gameUserService.deleteGameUser(gameCode, userId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
        } else if (gameCode != null) {
            List<GameUserSimpleResponseDto> responseDtoList = gameUserService.deleteGameUsersByGameCode(gameCode);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDtoList));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error("Invalid Parameters"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping
    public ResponseEntity<ResponseData<?>> findGameUser(@RequestParam(required = false) String code,
                                                        @RequestParam(required = false) String gameCode,
                                                        @RequestParam(required = false) Long userId) {
        if (code != null) {
            GameUserSimpleResponseDto responseDto = gameUserService.findGameUser(code);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
        } else if (gameCode != null && userId != null) {
            GameUserSimpleResponseDto responseDto = gameUserService.findGameUser(gameCode, userId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
        } else if (gameCode != null) {
            List<GameUserSimpleResponseDto> responseDtoList = gameUserService.findSimpleGameUsersByGameCode(gameCode);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDtoList));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error("Invalid Parameters"));
    }

    @GetMapping("/details")
    public ResponseEntity<ResponseData<?>> findGameUser(@RequestParam(required = false) String gameCode) {
        /* TODO: SimpleResponseDto 처럼 3가지 경우로 추가 기능 구현 필요 */
        List<GameUserDetailResponseDto> responseDtoList = gameUserService.findDetailGameUsersByGameCode(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDtoList));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PutMapping("/team")
    public ResponseEntity<ResponseData<?>> updateGameUserTeam(@RequestParam String team) {
        GameUserSimpleResponseDto responseDto = gameUserService.updateGameUserTeam(team);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @PutMapping("/score")
    public ResponseEntity<ResponseData<?>> updateGameUserScore(@RequestParam Integer score) {
        GameUserSimpleResponseDto responseDto = gameUserService.updateGameUserScore(score);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @PutMapping("/host")
    public ResponseEntity<ResponseData<?>> updateGameUserIsHost(@RequestParam Boolean host) {
        GameUserSimpleResponseDto responseDto = gameUserService.updateGameUserIsHost(host);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @PutMapping("/reset")
    public ResponseEntity<ResponseData<?>> resetGameUserScore(@RequestParam String gameCode) {
        List<GameUserSimpleResponseDto> responseDto = gameUserService.resetGameUserScore(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }
}
