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
    // 초성 유니코드 범위
    private static final int CHOSUNG_UNICODE_START = 0x1100;
    private static final int CHOSUNG_UNICODE_END = 0x1112;
    private static final char[] CHOSUNG_LIST = {
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };


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
        return 0.0;
    }

    public GameQuizDetailResponseDto setInitialSound(GameQuizDetailResponseDto quiz) {
        quiz.setKorObjAdjective(extractInitialSound(quiz.getKorObjAdjective()));
        quiz.setKorObject(extractInitialSound(quiz.getKorObject()));
        quiz.setKorSubAdjective(extractInitialSound(quiz.getKorSubAdjective()));
        quiz.setKorSubject(extractInitialSound(quiz.getKorSubject()));
        return quiz;
    }

    public static String extractInitialSound(String word) {
        StringBuilder result = new StringBuilder();

        for (char ch : word.toCharArray()) {
            if (ch >= '가' && ch <= '힣') {
                int chosungIndex = ((int) ch - CHOSUNG_UNICODE_START) / 28;
                result.append(CHOSUNG_LIST[chosungIndex]);
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }
}
