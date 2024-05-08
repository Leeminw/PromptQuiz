package com.ssafy.apm.gamequiz.service;

import com.ssafy.apm.gamequiz.dto.request.GameQuizCreateRequestDto;
import com.ssafy.apm.gamequiz.dto.request.GameQuizUpdateRequestDto;
import com.ssafy.apm.gamequiz.dto.response.GameQuizDetailResponseDto;
import com.ssafy.apm.gamequiz.dto.response.GameQuizSimpleResponseDto;

import java.util.List;

public interface GameQuizService {

    GameQuizSimpleResponseDto createGameQuiz(GameQuizCreateRequestDto requestDto);
    GameQuizSimpleResponseDto updateGameQuiz(GameQuizUpdateRequestDto requestDto);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    GameQuizSimpleResponseDto deleteGameQuiz(String code);
    List<GameQuizSimpleResponseDto> deleteGameQuizzesByGameCode(String gameCode);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    List<GameQuizSimpleResponseDto> findSimpleGameQuizzesByGameCode(String gameCode);
    List<GameQuizSimpleResponseDto> findSimpleGameQuizzesByGameCodeAndRound(String gameCode, Integer round);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    List<GameQuizDetailResponseDto> findDetailGameQuizzesByGameCode(String gameCode);
    List<GameQuizDetailResponseDto> findDetailGameQuizzesByGameCodeAndRound(String gameCode, Integer round);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    List<GameQuizSimpleResponseDto> findCurrentSimpleGameQuizzesByGameCode(String gameCode);
    List<GameQuizDetailResponseDto> findCurrentDetailGameQuizzesByGameCode(String gameCode);

}
