package com.ssafy.apm.game.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import com.ssafy.apm.game.dto.response.GameGetResponseDto;
import com.ssafy.apm.game.service.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
@Slf4j
public class GameController {

    private final GameServiceImpl gameService;

    @PostMapping()
    public ResponseEntity<ResponseData<?>> createGame(@RequestBody GameCreateRequestDto gameCreateRequestDto) {
        gameService.createGame(gameCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success("방 생성 완료"));
    }

    @GetMapping("/getGameList")
    public ResponseEntity<ResponseData<?>> getGameList(@RequestParam Long channelId) {
        List<GameGetResponseDto> dtoList = gameService.getGameList(channelId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방 목록 조회 완료",dtoList));
    }

    @GetMapping("/getGameInfo")
    public ResponseEntity<ResponseData<?>> getGameInfo(@RequestParam Long gameId) {
        GameGetResponseDto dto = gameService.getGameInfo(gameId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방 상세 정보 조회 완료",dto));
    }

    @PutMapping("/updateGameInfo")
    public ResponseEntity<ResponseData<?>> updateGameInfo(@RequestBody GameUpdateRequestDto gameUpdateRequestDto) {
        gameService.updateGameInfo(gameUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방 설정 수정 완료"));
    }

    @DeleteMapping()
    public ResponseEntity<ResponseData<?>> deleteGame(@RequestParam Long gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방 삭제 완료"));
    }
}
