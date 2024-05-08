package com.ssafy.apm.game.service;

import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import com.ssafy.apm.game.dto.response.GameGetResponseDto;

import java.util.List;

public interface GameService {

    Boolean createGameQuiz(String gameCode);

    GameGetResponseDto createGame(GameCreateRequestDto gameCreateRequestDto);

    List<GameGetResponseDto> getGameList(String channelCode);

    GameGetResponseDto getGameInfo(String gameCode);

    GameGetResponseDto updateGameInfo(GameUpdateRequestDto gameUpdateRequestDto);

    Integer updateGameRoundCnt(String gameCode, Boolean flag);

    Boolean updateGameIsStarted(String gameCode, Boolean isStarted);

    String deleteGame(String gameCode);
}
