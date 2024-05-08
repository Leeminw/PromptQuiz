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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*  Todo: 방 입장시 GameUserEntity 생성하고, 방 퇴장시 GameUserEntity 삭제하는 로직 필요( 다 Socket Handeler로 처리해야함 )
     *         레디하거나 팀을 바꾸거나 방장바꾸거나 하는 행동을 할 때 Update 처리가 필요하다
     * */

    /* REFACTORED: findGameUser() 으로 리팩토링됨 */
//    //    게임방 안에 있는 유저들 목록 가져옴( UserDB와 GameUserDB에 있는 데이터 불러옴)
//    @GetMapping("/gameUserList/{gameId}")
//    public ResponseEntity<ResponseData<?>> getGameUserList(@PathVariable(name = "gameId") Long gameId) {
//        List<GameUserDetailResponseDto> response = gameUserService.findDetailGameUsersByGameCode(gameId);
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
//    }

    /* REFACTORED: findGameUser() 으로 리팩토링됨 */
//    @GetMapping("/{gameUserId}")
//    public ResponseEntity<ResponseData<?>> getGameUser(@PathVariable(name = "gameUserId") Long gameUserId) {
//        GameUserSimpleResponseDto response = gameUserService.getGameUser(gameUserId);
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
//    }

    /* FIXME: 입장 요청을 GameController 에서 처리하고,
    *   입장 조건을 GameService 에서 판단하고, GameService 에서 createGameUser() 를 호출하는 것이 나아보임. */
//    // 게임 입장할 때 GameUserEntity 생성
//    @PostMapping("/enterGame/{gameId}")
//    public ResponseEntity<ResponseData<?>> postEnterGame(@PathVariable(name = "gameId") Long gameId) {
//        GameUserSimpleResponseDto response = gameUserService.postEnterGame(gameId);
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
//    }

    /* FIXME: GameController 로 이동하는 것이 나아보임 */
//    @PostMapping("/fastEnter")
//    public ResponseEntity<ResponseData<?>> postFastEnterGame() {
//        GameUserSimpleResponseDto response = gameUserService.postFastEnterGame();
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
//    }

    /* FIXME: GameController 로 이동하는 것이 나아보임 */
    /* FIXME 1: 퇴장 조건을 GameService 에서 판단하고,
    *   GameService 에서 deleteGameUser() 를 호출하는 것이 나아보임 */
    /* FIXME 2: 혹은 단순 deleteGameUser API 호출을 통해 처리하고,
    *   WebSocket 통신이 끊어지는 경우에도, deleteGameUser()를 호출하는 것이 나아보임*/
//    // 게임 퇴장할 때 GameUserEntity 삭제
//    @DeleteMapping("/exitGame/{gameId}")
//    public ResponseEntity<ResponseData<?>> deleteExitGame(@PathVariable(name = "gameId") Long gameId) {
////        삭제된 gameUserId 리턴
//        Long response = gameUserService.deleteExitGame(gameId);
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
//    }
}
