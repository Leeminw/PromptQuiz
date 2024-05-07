package com.ssafy.apm.gamequiz.service;

import com.ssafy.apm.gamequiz.dto.response.GameQuizGetResponseDto;

import java.util.List;

public interface GameQuizService {
    List<GameQuizGetResponseDto> getGameQuizListEachRoundByGameCode(String gameCode);

    List<GameQuizGetResponseDto> getAllGameQuizListByGameCode(String gameCode);

    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    String deleteGameQuiz(String gameCode);
}
