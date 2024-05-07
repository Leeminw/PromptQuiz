package com.ssafy.apm.game.service;

import com.ssafy.apm.game.domain.GameEntity;
import com.ssafy.apm.gamequiz.domain.GameQuizEntity;
import com.ssafy.apm.quiz.domain.Quiz;
import com.ssafy.apm.quiz.exception.QuizNotFoundException;
import com.ssafy.apm.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlankSubjectiveService {

    private final QuizRepository quizRepository;

    public List<GameQuizEntity> createGameQuizList(GameEntity gameEntity, Integer gameType, List<Quiz> quizList) {
        List<GameQuizEntity> response = new ArrayList<>();
        int curRound = 1;

        for (Quiz quiz : quizList) { // 빈칸 주관식은 답 하나만 넣어
            GameQuizEntity entity = GameQuizEntity.builder()
                    .gameCode(gameEntity.getCode())
                    .quizId(quiz.getId())
                    .type(gameType)
                    .round(curRound)
                    .isAnswer(true)
                    .build();
            response.add(entity);
        }
        return response;
    }
}
