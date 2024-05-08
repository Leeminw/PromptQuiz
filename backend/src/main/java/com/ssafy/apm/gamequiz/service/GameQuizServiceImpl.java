package com.ssafy.apm.gamequiz.service;

import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.game.exception.GameNotFoundException;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gamequiz.domain.GameQuizEntity;
import com.ssafy.apm.gamequiz.dto.response.GameQuizGetResponseDto;
import com.ssafy.apm.gamequiz.exception.GameQuizNotFoundException;
import com.ssafy.apm.gamequiz.repository.GameQuizRepository;
import com.ssafy.apm.gameuser.domain.GameUser;
import com.ssafy.apm.gameuser.exception.GameUserNotFoundException;
import com.ssafy.apm.gameuser.repository.GameUserRepository;
import com.ssafy.apm.multiplechoice.domain.MultipleChoiceEntity;
import com.ssafy.apm.multiplechoice.exception.MultipleChoiceNotFoundException;
import com.ssafy.apm.multiplechoice.repository.MultipleChoiceRepository;
import com.ssafy.apm.quiz.domain.Quiz;
import com.ssafy.apm.quiz.exception.QuizNotFoundException;
import com.ssafy.apm.quiz.repository.QuizRepository;
import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
