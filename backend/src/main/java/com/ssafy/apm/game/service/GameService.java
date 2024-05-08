package com.ssafy.apm.game.service;

import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import com.ssafy.apm.game.dto.response.GameResponseDto;

import java.util.List;

public interface GameService {

    GameResponseDto createGame(GameCreateRequestDto requestDto);
    GameResponseDto updateGame(GameUpdateRequestDto requestDto);
    GameResponseDto deleteGame(String code);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    GameResponseDto findGameByGameCode(String gameCode);
    List<GameResponseDto> findGamesByChannelCode(String channelCode);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*  Todo: 라운드 증가하고 증가된 라운드 값 리턴,
     *   더 이상 라운드가 없을때( 마지막 라운드 끝나고 난 뒤 ) return 값을 -1
     *   현재 라운드의 게임-문제 매핑 테이블 리턴
     * */
    Integer updateGameRoundCnt(String gameCode, Boolean flag);

}
