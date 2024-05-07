package com.ssafy.apm.gameuser.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.gameuser.dto.response.GameUserDetailResponseDto;
import com.ssafy.apm.gameuser.dto.response.GameUserResponseDto;
import com.ssafy.apm.gameuser.service.GameUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/game-user")
@RequiredArgsConstructor
@Slf4j
public class GameUserController {

    private final GameUserService gameUserService;

    /*  Todo: 방 입장시 GameUserEntity 생성하고, 방 퇴장시 GameUserEntity 삭제하는 로직 필요( 다 Socket Handeler로 처리해야함 )
     *         레디하거나 팀을 바꾸거나 방장바꾸거나 하는 행동을 할 때 Update 처리가 필요하다
     * */

    //    게임방 안에 있는 유저들 목록 가져옴( UserDB와 GameUserDB에 있는 데이터 불러옴)
    @GetMapping("/gameUserList/{gameId}")
    public ResponseEntity<ResponseData<?>> getGameUserList(@PathVariable(name = "gameId") Long gameId) {
        List<GameUserDetailResponseDto> response = gameUserService.getGameUserList(gameId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }

    @GetMapping("/{gameUserId}")
    public ResponseEntity<ResponseData<?>> getGameUser(@PathVariable(name = "gameUserId") Long gameUserId) {
        GameUserResponseDto response = gameUserService.getGameUser(gameUserId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }

    //    게임 입장할 때 GameUserEntity 생성
    @PostMapping("/enterGame/{gameId}")
    public ResponseEntity<ResponseData<?>> postEnterGame(@PathVariable(name = "gameId") Long gameId) {
        GameUserResponseDto response = gameUserService.postEnterGame(gameId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }

    @PostMapping("/fastEnter")
    public ResponseEntity<ResponseData<?>> postFastEnterGame() {
        GameUserResponseDto response = gameUserService.postFastEnterGame();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }

    //    게임 퇴장할 때 GameUserEntity 삭제
    @DeleteMapping("/exitGame/{gameId}")
    public ResponseEntity<ResponseData<?>> deleteExitGame(@PathVariable(name = "gameId") Long gameId) {
//        삭제된 gameUserId 리턴
        Long response = gameUserService.deleteExitGame(gameId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }
}
