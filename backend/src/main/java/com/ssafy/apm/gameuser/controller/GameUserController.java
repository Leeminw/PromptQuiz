package com.ssafy.apm.gameuser.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.gameuser.dto.response.GameUserDetailResponseDto;
import com.ssafy.apm.gameuser.service.GameUserServiceImpl;
import com.ssafy.apm.user.dto.UserDetailResponseDto;
import com.ssafy.apm.userchannel.service.UserChannelServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/game-user")
@RequiredArgsConstructor
@Slf4j
public class GameUserController {

    private final GameUserServiceImpl gameUserService;

    /*  Todo: 방 입장시 GameUserEntity 생성하고, 방 퇴장시 GameUserEntity 삭제하는 로직 필요
    *         레디하거나 팀을 바꾸거나 하는 행동을 할 때 Update 처리가 필요하다
    * */

//    게임방 안에 있는 유저들 목록 가져옴( UserDB와 GameUserDB에 있는 데이터 불러옴)
    @GetMapping("/getGameUserList")
    public ResponseEntity<ResponseData<?>> getGameUserList(@RequestParam Long gameId) {
        List<GameUserDetailResponseDto> dtoList = gameUserService.getGameUserList(gameId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(dtoList));
    }
}
