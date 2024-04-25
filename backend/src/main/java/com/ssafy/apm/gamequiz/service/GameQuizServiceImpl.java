package com.ssafy.apm.gamequiz.service;

import com.ssafy.apm.game.domain.GameEntity;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gamequiz.domain.GameQuizEntity;
import com.ssafy.apm.gamequiz.dto.response.GameQuizGetResponseDto;
import com.ssafy.apm.gamequiz.repository.GameQuizRepository;
import com.ssafy.apm.quiz.domain.Quiz;
import com.ssafy.apm.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameQuizServiceImpl implements GameQuizService {

    private final GameQuizRepository gameQuizRepository;
    private final GameRepository gameRepository;
    private final QuizRepository quizRepository;

    //    맨 앞에 있는 놈을 뽑아서 보내줌
    @Override
    public GameQuizGetResponseDto getGameQuizDetail(Long gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게임방입니다."));

        Integer round = gameEntity.getRounds();
//        정답 주는거
        GameQuizEntity entity = gameQuizRepository.findByGameIdAndRound(gameId, round)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 퀴즈입니다."));

        GameQuizGetResponseDto response = new GameQuizGetResponseDto(entity);

        return response;
    }

    @Override
    @Transactional
    public Boolean createAnswerGameQuiz(Long gameId) {
        List<Quiz> quizList = quizRepository.findAllQuizRandom(10);
        List<GameQuizEntity> gameQuizEntityList = new ArrayList<>();
        Integer currentRound = 1;
        for (Quiz quiz : quizList) {
            GameQuizEntity entity = GameQuizEntity.builder()
                    .gameId(gameId)
                    .quizId(quiz.getId())
                    .round(currentRound)
                    .type(1) // 임시로 1넣었는데 나중에 방설정에 따라서 ㄱㄱ
                    .build();
            currentRound += 1;
            gameQuizEntityList.add(entity);
        }

        gameQuizRepository.saveAll(gameQuizEntityList);
        return true;
    }
}
