package com.ssafy.apm.game.service;

import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import com.ssafy.apm.game.dto.response.GameResponseDto;

import java.util.List;

public interface GameService {

    GameResponseDto createGame(GameCreateRequestDto requestDto);
    Boolean createGameQuiz(String gameCode);
    List<GameResponseDto> findGamesByChannelCode(String channelCode);
    GameResponseDto findGameByGameCode(String gameCode);
    GameResponseDto updateGame(GameUpdateRequestDto gameUpdateRequestDto);
    Integer updateGameRoundCnt(String gameCode, Boolean flag);
    Game updateGameIsStarted(String gameCode, Boolean isStarted);
    GameResponseDto deleteGame(String code);
}
