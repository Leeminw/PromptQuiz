package com.ssafy.apm.game.service;

import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.quiz.domain.Quiz;
import com.ssafy.apm.gamequiz.domain.GameQuiz;
import com.ssafy.apm.gameuser.service.GameUserService;
import com.ssafy.apm.gamequiz.service.GameQuizService;
import com.ssafy.apm.socket.dto.request.GameChatRequestDto;
import com.ssafy.apm.gamequiz.dto.response.GameQuizDetailResponseDto;

import lombok.RequiredArgsConstructor;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlankSubjectiveService {

    private final GameUserService gameUserService;
    private final GameQuizService gameQuizService;

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

    @Transactional
    public HashMap<String, Double> evaluateAnswers(GameChatRequestDto answer, Set<String> checkPrompt) {
        GameQuizDetailResponseDto quiz = gameQuizService.findFirstCurrentDetailGameQuizByGameCode(answer.getGameCode());
        HashMap<String, Double> resultMap = new HashMap<>();
        for (String prompt : checkPrompt) {
            Double rate = 0.0;
            switch (prompt) {
                case "kor_subject" -> rate = calculate(quiz.getKorSubject(), answer.getContent());
                case "kor_sub_adjective" -> rate = calculate(quiz.getKorSubAdjective(), answer.getContent());
                case "kor_object" -> rate = calculate(quiz.getKorObject(), answer.getContent());
                case "kor_obj_adjective" -> rate = calculate(quiz.getKorObjAdjective(), answer.getContent());
            }
            if (rate >= 0.9) {
                /* todo: 유저 점수 올리기 (맞춤 처리를 어떻게 할 것인가..
                    transaction처리가 되야 한다. 그럼 DB로 맞춘사람 관리를 해야 되는데..
                */
                gameUserService.updateGameUserScore(answer.getUserId(), 5);
            }
            resultMap.put(prompt, rate);
        }

        return resultMap;
    }

    public static double calculate(String str1, String str2) {
        Map<String, Integer> vector1 = getTermFrequencyVector(str1);
        Map<String, Integer> vector2 = getTermFrequencyVector(str2);

        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;

        for (String term : vector1.keySet()) {
            if (vector2.containsKey(term)) {
                dotProduct += vector1.get(term) * vector2.get(term);
            }
            magnitude1 += Math.pow(vector1.get(term), 2);
        }

        for (String term : vector2.keySet()) {
            magnitude2 += Math.pow(vector2.get(term), 2);
        }

        if (magnitude1 == 0 || magnitude2 == 0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(magnitude1) * Math.sqrt(magnitude2));
    }

    private static Map<String, Integer> getTermFrequencyVector(String str) {
        Map<String, Integer> vector = new HashMap<>();
        String[] terms = str.split("\\s+");

        for (String term : terms) {
            vector.put(term, vector.getOrDefault(term, 0) + 1);
        }

        return vector;
    }
}
