package com.ssafy.apm.game.service;

import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import com.ssafy.apm.game.dto.response.GameResponseDto;
import com.ssafy.apm.gameuser.dto.response.GameUserSimpleResponseDto;
import com.ssafy.apm.quiz.dto.request.QuizRequestDto;
import com.ssafy.apm.quiz.dto.response.QuizResponseDto;

import java.util.List;

public interface GameService {

    GameResponseDto createGame(GameCreateRequestDto requestDto);

    Boolean createGameQuiz(String gameCode);

    GameUserSimpleResponseDto enterGame(String gameCode);

    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    List<GameResponseDto> findGamesByChannelCode(String channelCode);

    GameResponseDto findGameByGameCode(String gameCode);

    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    GameResponseDto updateGame(GameUpdateRequestDto gameUpdateRequestDto);

    Integer updateGameRoundCnt(String gameCode, Boolean flag);

    void updateUserScore(String gameCode);

    GameResponseDto updateGameIsStarted(String gameCode, Boolean isStarted);

    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    GameResponseDto deleteGame(String code);

    String exitGame(String gameCode);

    String exitGameByUserId(Long userId, String gameCode);


    List<QuizResponseDto> createAnswerGameQuizCanShow(String gameCode);

    Integer getMaxTimeByGameCode(String gameCode);
}
