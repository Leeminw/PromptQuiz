package com.ssafy.apm.game.service;

import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.gamequiz.domain.GameQuiz;
import com.ssafy.apm.quiz.domain.Quiz;
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

    public List<GameQuiz> createGameQuizList(Game gameEntity, Integer gameType, List<Quiz> quizList) {
        List<GameQuiz> response = new ArrayList<>();
        int curRound = 1;

        for (Quiz quiz : quizList) { // 빈칸 주관식은 답 하나만 넣어
            GameQuiz entity = GameQuiz.builder()
                    .gameCode(gameEntity.getCode())
                    .quizId(quiz.getId())
                    .type(gameType)
                    .round(curRound)
                    .isAnswer(true)
                    .build();
            response.add(entity);
            curRound++;
        }
        return response;
    }

    public GameQuiz createGameQuiz(Game gameEntity, Quiz quiz, int curRound) {
        return GameQuiz.builder()
                .gameCode(gameEntity.getCode())
                .quizId(quiz.getId())
                .type(4)
                .round(curRound)
                .isAnswer(true)
                .build();
    }
}
