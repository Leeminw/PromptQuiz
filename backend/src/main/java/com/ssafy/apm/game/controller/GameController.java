package com.ssafy.apm.game.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.service.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
@Slf4j
public class GameController {

    private final GameServiceImpl gameService;

    @PostMapping()
    public ResponseEntity<ResponseData<?>> createGame(@RequestBody GameCreateRequestDto gameCreateRequestDto) {
        log.debug("gameCreateDto : {}" + gameCreateRequestDto);
        gameService.createGame(gameCreateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success());
    }

    @GetMapping("/getGameList")
    public ResponseEntity<ResponseData<?>> getGameList() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success());
    }

    @DeleteMapping()
    public ResponseEntity<ResponseData<?>> deleteGame() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success());
    }
}
