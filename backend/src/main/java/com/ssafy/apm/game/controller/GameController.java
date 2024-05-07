package com.ssafy.apm.game.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import com.ssafy.apm.game.dto.response.GameGetResponseDto;
import com.ssafy.apm.game.service.GameService;
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

    /*    Todo: 빠른 대전시 방 목록을 뒤져서 빈 방에 들어가게 할건지( 빈 방이 없다면 방 생성 )
     *           아니면 채널마다 큐를 만들어서 이 큐에 넣을건지( 좀 복잡해 ) 선택하고 API 만들어야함
     *
     * */


    //    방 생성
//    방 만드는 사람의 gameUser data생성( 방장이니까 )
    @PostMapping("")
    public ResponseEntity<ResponseData<?>> createGame(@RequestBody GameCreateRequestDto gameCreateRequestDto) {
        GameGetResponseDto response = gameService.createGame(gameCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success("방 생성 완료", response));
    }

    //    방 목록들 조회
    @GetMapping("/gameList/{channelCode}")
    public ResponseEntity<ResponseData<?>> getGameList(@PathVariable(name = "channelCode") String channelCode) {
        List<GameGetResponseDto> response = gameService.getGameList(channelCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방 목록 조회 완료", response));
    }

    //    방, 채널 상세 정보 조회
    @GetMapping("/gameInfo/{gameCode}")
    public ResponseEntity<ResponseData<?>> getGameAndChannelInfo(@PathVariable(name = "gameCode") String gameCode) {
        GameGetResponseDto response = gameService.getGameInfo(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방, 채널 상세 정보 조회 완료", response));
    }

    //    방 정보 수정 API
    @PutMapping("/gameInfo")
    public ResponseEntity<ResponseData<?>> updateGameInfo(@RequestBody GameUpdateRequestDto gameUpdateRequestDto) {
        GameGetResponseDto response = gameService.updateGameInfo(gameUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방 설정 수정 완료", response));
    }

    @DeleteMapping("/{gameCode}")
    public ResponseEntity<ResponseData<?>> deleteGame(@PathVariable(name = "gameCode") String gameCode) {
        String response = gameService.deleteGame(gameCode);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("방 삭제 완료", response));
    }
}
