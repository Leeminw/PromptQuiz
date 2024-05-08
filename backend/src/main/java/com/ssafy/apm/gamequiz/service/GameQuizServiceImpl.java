package com.ssafy.apm.gamequiz.service;

import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.game.exception.GameNotFoundException;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gamequiz.domain.GameQuizEntity;
import com.ssafy.apm.gamequiz.repository.GameQuizRepository;
import com.ssafy.apm.gamequiz.dto.response.GameQuizGetResponseDto;
import com.ssafy.apm.gamequiz.exception.GameQuizNotFoundException;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameQuizServiceImpl implements GameQuizService {

    private final GameQuizRepository gameQuizRepository;
    private final GameRepository gameRepository;

    @Override
    public List<GameQuizGetResponseDto> getGameQuizListEachRoundByGameCode(String gameCode) {
        Game game = gameRepository.findByCode(gameCode)
                .orElseThrow(() -> new GameNotFoundException(gameCode));
        List<GameQuizEntity> gameQuizList = gameQuizRepository.findAllByGameCodeAndRound(gameCode, game.getCurRounds())
                .orElseThrow(() -> new GameQuizNotFoundException("No entities exists by gameCode, round"));

        return gameQuizList.stream().map(GameQuizGetResponseDto::new).toList();
    }

    @Override
    public GameQuizGetResponseDto getCurRoundGameQuizByGameCode(String gameCode) {
        Game game = gameRepository.findByCode(gameCode)
                .orElseThrow(() -> new GameNotFoundException(gameCode));

        GameQuizEntity gameQuiz = gameQuizRepository.findByGameCodeAndRound(gameCode, game.getCurRounds())
                .orElseThrow(() -> new GameQuizNotFoundException("No entities exists by gameCode, round"));

        return new GameQuizGetResponseDto(gameQuiz);
    }

    @Override
    public List<GameQuizGetResponseDto> getAllGameQuizListByGameCode(String gameCode) {
        List<GameQuizEntity> gameQuizList = gameQuizRepository.findAllByGameCode(gameCode)
                .orElseThrow(() -> new GameQuizNotFoundException("No entities exists by gameCode"));

        return gameQuizList.stream().map(GameQuizGetResponseDto::new).toList();
    }

    @Override
    @Transactional
    public String deleteGameQuiz(String gameCode) {
        List<GameQuizEntity> gameQuizEntityList = gameQuizRepository.findAllByGameCode(gameCode)
                .orElseThrow(() -> new GameQuizNotFoundException("No entities exists by gameCode"));

        gameQuizRepository.deleteAll(gameQuizEntityList);
        return gameCode;
    }
}
