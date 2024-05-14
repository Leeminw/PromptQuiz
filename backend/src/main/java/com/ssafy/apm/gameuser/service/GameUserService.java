package com.ssafy.apm.gameuser.service;

import com.ssafy.apm.gameuser.dto.request.GameUserCreateRequestDto;
import com.ssafy.apm.gameuser.dto.request.GameUserUpdateRequestDto;
import com.ssafy.apm.gameuser.dto.response.GameUserDetailResponseDto;
import com.ssafy.apm.gameuser.dto.response.GameUserSimpleResponseDto;

import java.util.List;

public interface GameUserService {
    GameUserSimpleResponseDto createGameUser(GameUserCreateRequestDto requestDto);
    GameUserSimpleResponseDto updateGameUser(GameUserUpdateRequestDto requestDto);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    GameUserSimpleResponseDto deleteGameUser(String code);
    GameUserSimpleResponseDto deleteGameUser(String gameCode, Long userId);
    List<GameUserSimpleResponseDto> deleteGameUsersByGameCode(String gameCode);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    GameUserSimpleResponseDto findGameUser(String code);
    GameUserSimpleResponseDto findGameUser(String gameCode, Long userId);
    List<GameUserSimpleResponseDto> findSimpleGameUsersByGameCode(String gameCode);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* TODO: SimpleResponseDto 처럼 3가지 경우로 추가 기능 구현 필요 */
    List<GameUserDetailResponseDto> findDetailGameUsersByGameCode(String gameCode);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    GameUserSimpleResponseDto updateGameUserTeam(String team);
    GameUserSimpleResponseDto updateGameUserScore(Integer score);
    GameUserSimpleResponseDto updateGameUserIsHost(Boolean isHost);
    GameUserSimpleResponseDto updateGameUserScore(Long userId, Integer score);
    List<GameUserSimpleResponseDto> resetGameUserScore(String gameCode);

}
