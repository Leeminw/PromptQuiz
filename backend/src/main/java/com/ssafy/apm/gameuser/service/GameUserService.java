package com.ssafy.apm.gameuser.service;

import com.ssafy.apm.gameuser.dto.response.GameUserDetailResponseDto;
import com.ssafy.apm.gameuser.dto.response.GameUserGetResponseDto;
import com.ssafy.apm.user.dto.UserDetailResponseDto;

import java.util.List;

public interface GameUserService {

    List<GameUserDetailResponseDto> getGameUserList(Long gameId);
    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    GameUserGetResponseDto postEnterGame(Long gameId);
    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    GameUserGetResponseDto updateGameUserScore(Integer score);
    GameUserGetResponseDto updateGameUserIsReady(Boolean isReady);
    GameUserGetResponseDto updateGameUserTeam(String team);
    void updateUserScore(Long gameId);
    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////

    Long deleteExitGame(Long gameId);
}
