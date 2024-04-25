package com.ssafy.apm.game.service;

import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import com.ssafy.apm.game.dto.response.GameGetResponseDto;

import java.util.List;

public interface GameService {
    /*  Todo: 라운드 증가하고 증가된 라운드 값 리턴,
     *   더 이상 라운드가 없을때( 마지막 라운드 끝나고 난 뒤 ) return 값을 -1
     *   현재 라운드의 게임-문제 매핑 테이블 리턴
     *
     * */
    GameGetResponseDto createGame(GameCreateRequestDto gameCreateRequestDto);

    List<GameGetResponseDto> getGameList(Long channelId);

    GameGetResponseDto getGameInfo(Long gameId);
    GameGetResponseDto updateGameInfo(GameUpdateRequestDto gameUpdateRequestDto);
    Integer updateGameRoundCnt(Long gameId);
    Long deleteGame(Long gameId);
}
