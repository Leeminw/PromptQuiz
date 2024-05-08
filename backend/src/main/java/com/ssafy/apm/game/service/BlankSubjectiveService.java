package com.ssafy.apm.game.service;

import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.gameuser.service.GameUserService;
import com.ssafy.apm.quiz.domain.Quiz;
import com.ssafy.apm.gamequiz.domain.GameQuiz;
import com.ssafy.apm.gamequiz.service.GameQuizService;
import com.ssafy.apm.gamequiz.dto.response.GameQuizDetailResponseDto;
import com.ssafy.apm.socket.dto.request.GameChatRequestDto;

import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.apache.commons.text.similarity.CosineDistance;
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
    public HashMap<String, Double> evaluateAnswers(GameChatRequestDto answer, Set<String> checkPrompt){
        GameQuizDetailResponseDto quiz = gameQuizService.findFirstCurrentDetailGameQuizByGameCode(answer.getGameCode());
        HashMap<String, Double> resultMap = new HashMap<>();
        for (String prompt : checkPrompt) {
            Double rate = 0.0;
            switch (prompt){
                case "kor_subject" -> rate = calculateSimilarity(quiz.getKorSubject(), answer.getContent());
                case "kor_sub_adjective" -> rate = calculateSimilarity(quiz.getKorSubAdjective(), answer.getContent());
                case "kor_object" -> rate = calculateSimilarity(quiz.getKorObject(), answer.getContent());
                case "kor_obj_adjective" -> rate = calculateSimilarity(quiz.getKorObjAdjective(), answer.getContent());
            }
            if(rate >= 0.9){
                /* todo: 유저 점수 올리기 (맞춤 처리를 어떻게 할 것인가..
                    transaction처리가 되야 한다. 그럼 DB로 맞춘사람 관리를 해야 되는데..
                */
                // gameQuizService.updateGameQuiz();
                // gameUserService.updateGameUserScore(answer.getUserId(), 5);
            }
            resultMap.put(prompt, rate);
        }

        return resultMap;
    }

    private Double calculateSimilarity(String input, String answer){
        input = input.replace(" ", "");
        answer = answer.replace(" ", "");

        CosineDistance ld = new CosineDistance();
        int maxLen = Math.max(input.length(), answer.length());
        double temp = ld.apply(input, answer);
        return (maxLen - temp) / maxLen;
    }
}
