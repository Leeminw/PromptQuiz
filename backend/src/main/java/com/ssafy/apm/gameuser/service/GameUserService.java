package com.ssafy.apm.gameuser.service;

import com.ssafy.apm.gameuser.dto.response.GameUserDetailResponseDto;
import com.ssafy.apm.gameuser.dto.response.GameUserGetResponseDto;
import com.ssafy.apm.user.dto.UserDetailResponseDto;

import java.util.List;

public interface GameUserService {

    List<GameUserDetailResponseDto> getGameUserList(String gameCode);

    GameUserGetResponseDto getGameUser(String code);

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    GameUserGetResponseDto postEnterGame(String gameCode);

//    GameUserGetResponseDto postFastEnterGame();

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    GameUserGetResponseDto updateGameUserScore(Integer score);

    GameUserGetResponseDto updateGameUserTeam(String team);

    void updateUserScore(String gameCode);
    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////

    String deleteExitGame(String gameCode);

    String deleteExitGameByUserId(Long userId, String gameCode);
}
