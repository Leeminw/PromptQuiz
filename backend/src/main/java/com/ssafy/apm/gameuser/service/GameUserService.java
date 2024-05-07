package com.ssafy.apm.gameuser.service;

import com.ssafy.apm.gameuser.dto.response.GameUserDetailResponseDto;
import com.ssafy.apm.gameuser.dto.response.GameUserResponseDto;

import java.util.List;

public interface GameUserService {

    List<GameUserDetailResponseDto> getGameUserList(String gameCode);

    GameUserResponseDto getGameUser(Long gameUserId);

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    GameUserResponseDto postEnterGame(String gameCode);

    GameUserResponseDto postFastEnterGame();

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    GameUserResponseDto updateGameUserScore(Integer score);

    GameUserResponseDto updateGameUserIsReady(Boolean isReady);

    GameUserResponseDto updateGameUserTeam(String team);

    void updateUserScore(String gameCode);
    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////

    Long deleteExitGame(Long gameId);

    Long deleteExitGameByUserId(Long userId, String gameCode);
}
