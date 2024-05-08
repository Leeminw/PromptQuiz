package com.ssafy.apm.gamequiz.service;

import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.game.exception.GameNotFoundException;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gamequiz.repository.GameQuizRepository;
import com.ssafy.apm.gamequiz.exception.GameQuizNotFoundException;

import com.ssafy.apm.gamequiz.domain.GameQuiz;
import com.ssafy.apm.gamequiz.dto.request.GameQuizCreateRequestDto;
import com.ssafy.apm.gamequiz.dto.request.GameQuizUpdateRequestDto;
import com.ssafy.apm.gamequiz.dto.response.GameQuizDetailResponseDto;
import com.ssafy.apm.gamequiz.dto.response.GameQuizSimpleResponseDto;
import com.ssafy.apm.quiz.exception.QuizNotFoundException;
import com.ssafy.apm.quiz.repository.QuizRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameQuizServiceImpl implements GameQuizService {

    private final GameRepository gameRepository;
    private final QuizRepository quizRepository;
    private final GameQuizRepository gameQuizRepository;

    @Override
    public GameQuizSimpleResponseDto createGameQuiz(GameQuizCreateRequestDto requestDto) {
        GameQuiz gameQuiz = gameQuizRepository.save(requestDto.toEntity());
        return new GameQuizSimpleResponseDto(gameQuiz);
    }

    @Override
    public GameQuizSimpleResponseDto updateGameQuiz(GameQuizUpdateRequestDto requestDto) {
        GameQuiz gameQuiz = gameQuizRepository.findById(requestDto.getCode()).orElseThrow(
                () -> new GameQuizNotFoundException("GameQuiz Not Found with code: " + requestDto.getCode()));
        return new GameQuizSimpleResponseDto(gameQuizRepository.save(gameQuiz.update(requestDto)));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    @Transactional
    public GameQuizSimpleResponseDto deleteGameQuiz(String code) {
        GameQuiz gameQuiz = gameQuizRepository.findById(code).orElseThrow(
                () -> new GameQuizNotFoundException("GameQuiz Not Found with code: " + code));
        gameQuizRepository.delete(gameQuiz);
        return new GameQuizSimpleResponseDto(gameQuiz);
    }

    @Override
    @Transactional
    public List<GameQuizSimpleResponseDto> deleteGameQuizzesByGameCode(String gameCode) {
        List<GameQuiz> gameQuizzes = gameQuizRepository.findAllByGameCode(gameCode).orElseThrow(
                () -> new GameQuizNotFoundException("GameQuizzes Not Found with GameCode: " + gameCode));
        gameQuizRepository.deleteAll(gameQuizzes);
        return gameQuizzes.stream().map(GameQuizSimpleResponseDto::new).toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<GameQuizSimpleResponseDto> findSimpleGameQuizzesByGameCode(String gameCode) {
        List<GameQuiz> gameQuizzes = gameQuizRepository.findAllByGameCode(gameCode).orElseThrow(
                () -> new GameQuizNotFoundException("GameQuizzes Not Found with GameCode: " + gameCode));
        return gameQuizzes.stream().map(GameQuizSimpleResponseDto::new).toList();
    }

    @Override
    public List<GameQuizSimpleResponseDto> findSimpleGameQuizzesByGameCodeAndRound(String gameCode, Integer round) {
        List<GameQuiz> gameQuizzes = gameQuizRepository.findAllByGameCodeAndRound(gameCode, round).orElseThrow(
                () -> new GameQuizNotFoundException("GameQuizzes Not Found with GameCode, Round" +
                        gameCode + ", " + round));
        return gameQuizzes.stream().map(GameQuizSimpleResponseDto::new).toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<GameQuizDetailResponseDto> findDetailGameQuizzesByGameCode(String gameCode) {
        List<GameQuiz> gameQuizzes = gameQuizRepository.findAllByGameCode(gameCode).orElseThrow(
                () -> new GameQuizNotFoundException("GameQuizzes Not Found with GameCode: " + gameCode));
        return gameQuizzes.stream().map(gameQuiz -> new GameQuizDetailResponseDto(gameQuiz,
                quizRepository.findById(gameQuiz.getQuizId()).orElseThrow(
                        () -> new QuizNotFoundException(gameQuiz.getQuizId()))
        )).toList();
    }

    @Override
    public List<GameQuizDetailResponseDto> findDetailGameQuizzesByGameCodeAndRound(String gameCode, Integer round) {
        List<GameQuiz> gameQuizzes = gameQuizRepository.findAllByGameCodeAndRound(gameCode, round).orElseThrow(
                () -> new GameQuizNotFoundException("GameQuizzes Not Found with GameCode, Round" +
                        gameCode + ", " + round));
        return gameQuizzes.stream().map(gameQuiz -> new GameQuizDetailResponseDto(gameQuiz,
                quizRepository.findById(gameQuiz.getQuizId()).orElseThrow(
                        () -> new QuizNotFoundException(gameQuiz.getQuizId()))
        )).toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<GameQuizSimpleResponseDto> findCurrentSimpleGameQuizzesByGameCode(String gameCode) {
        Game game = gameRepository.findByCode(gameCode).orElseThrow(() -> new GameNotFoundException(gameCode));
        List<GameQuiz> gameQuizzes = gameQuizRepository.findAllByGameCodeAndRound(gameCode, game.getCurRounds())
                .orElseThrow(() -> new GameQuizNotFoundException("GameQuizzes Not Found with GameCode, Round" +
                        gameCode + ", " + game.getCurRounds()));
        return gameQuizzes.stream().map(GameQuizSimpleResponseDto::new).toList();
    }

    @Override
    public List<GameQuizDetailResponseDto> findCurrentDetailGameQuizzesByGameCode(String gameCode) {
        Game game = gameRepository.findByCode(gameCode).orElseThrow(() -> new GameNotFoundException(gameCode));
        List<GameQuiz> gameQuizzes = gameQuizRepository.findAllByGameCodeAndRound(gameCode, game.getCurRounds())
                .orElseThrow(() -> new GameQuizNotFoundException("GameQuizzes Not Found with GameCode, Round" +
                        gameCode + ", " + game.getCurRounds()));
        return gameQuizzes.stream().map(gameQuiz -> new GameQuizDetailResponseDto(gameQuiz,
                quizRepository.findById(gameQuiz.getQuizId()).orElseThrow(
                        () -> new QuizNotFoundException(gameQuiz.getQuizId()))
        )).toList();
    }

    @Override
    public GameQuizDetailResponseDto findFirstCurrentDetailGameQuizByGameCode(String gameCode) {
        Game game = gameRepository.findByCode(gameCode).orElseThrow(() -> new GameNotFoundException(gameCode));
        GameQuiz gameQuiz = gameQuizRepository.findFirstByGameCodeAndRound(gameCode, game.getCurRounds())
                .orElseThrow(() -> new GameQuizNotFoundException("GameQuiz Not Found with GameCode, Round" +
                        gameCode + ", " + game.getCurRounds()));
        return new GameQuizDetailResponseDto(gameQuiz, quizRepository.findById(gameQuiz.getQuizId())
                .orElseThrow( () -> new QuizNotFoundException(gameQuiz.getQuizId())));
    }

    @Override
    public Integer getCurrentGameQuizTypeByGameCode(String gameCode) {
        Game game = gameRepository.findByCode(gameCode).orElseThrow(() -> new GameNotFoundException(gameCode));

        GameQuiz gameQuiz = gameQuizRepository.findFirstByGameCodeAndRound(gameCode, game.getCurRounds())
                .orElseThrow(() -> new GameQuizNotFoundException("GameQuiz Not Found with GameCode, Round" +
                        gameCode + ", " + game.getCurRounds()));

        return gameQuiz.getType();
    }
}
