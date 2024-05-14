package com.ssafy.apm.game.service;

import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.quiz.domain.Quiz;
import com.ssafy.apm.gamequiz.domain.GameQuiz;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.apm.gameuser.service.GameUserService;
import com.ssafy.apm.gamequiz.service.GameQuizService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.apm.socket.dto.request.GameChatRequestDto;
import com.ssafy.apm.gamequiz.dto.response.GameQuizDetailResponseDto;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.nio.charset.StandardCharsets;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.ResponseHandler;
import org.springframework.stereotype.Service;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.BasicResponseHandler;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlankSubjectiveService {
    // 초성 유니코드 범위
    private static final int CHOSUNG_UNICODE_START = 0xAC00;

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
            double rate = 0.0;
            switch (prompt) {
                case "kor_subject" -> rate = calculate(quiz.getKorSubject(), answer.getContent());
                case "kor_sub_adjective" -> rate = calculate(quiz.getKorSubAdjective(), answer.getContent());
                case "kor_object" -> rate = calculate(quiz.getKorObject(), answer.getContent());
                case "kor_obj_adjective" -> rate = calculate(quiz.getKorObjAdjective(), answer.getContent());
            }
            rate = Math.round(rate * 10000.0) / 100.0;
            if (rate >= 100.0) {
                gameUserService.updateGameUserScore(answer.getUserId(), 5);
            }
            resultMap.put(prompt, rate);
        }

        return resultMap;
    }

    public static double levenshteinDistanceCalculate(String str1, String str2) {
        str1 = str1.replace(" ", "");
        str2 = str2.replace(" ", "");
        LevenshteinDistance ld = new LevenshteinDistance();
        int maxLen = Math.max(str1.length(), str2.length());
        double temp = ld.apply(str1, str2);
        return (maxLen - temp) / maxLen;
    }

    public static double calculate(String str1, String str2) {
        double similarity = 0.0;
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost postRequest = new HttpPost("http://k10a509.p.ssafy.io:8000/similarity");
            postRequest.setHeader("Content-Type", "application/json; charset=UTF-8");
            postRequest.setEntity(buildBody(str1, str2));

            HttpResponse response = httpClient.execute(postRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                similarity = parseBody(handler.handleResponse(response));
            } else if (statusCode == 404) {
                similarity = levenshteinDistanceCalculate(str1, str2);
            } else {
                log.debug("response is error : " + response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            log.debug(e.getMessage());
        }

        return similarity;
    }

    public static StringEntity buildBody(String str1, String str2) {
        str1 = str1.replace(" ", "");
        str2 = str2.replace(" ", "");

        String requestBody = String.format("{\"word1\": \"%s\", \"word2\": \"%s\"}", str1, str2);
        return new StringEntity(requestBody, StandardCharsets.UTF_8);
    }

    public static double parseBody(String responseBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(responseBody);
        return jsonNode.get("similarity").asDouble();
    }

    public GameQuizDetailResponseDto setInitialSound(GameQuizDetailResponseDto quiz) {
        try {
            quiz.setKorObjAdjective(extractInitialSound(quiz.getKorObjAdjective()));
            quiz.setKorObject(extractInitialSound(quiz.getKorObject()));
            quiz.setKorSubAdjective(extractInitialSound(quiz.getKorSubAdjective()));
            quiz.setKorSubject(extractInitialSound(quiz.getKorSubject()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return quiz;
    }

    public static String extractInitialSound(String word) {
        StringBuilder result = new StringBuilder();

        for (char ch : word.toCharArray()) {
            if (ch >= '가' && ch <= '힣') {
                int chosungIndex = ((int) ch - CHOSUNG_UNICODE_START) / (21 * 28);
                result.append(CHOSUNG_LIST[chosungIndex]);
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }
}
