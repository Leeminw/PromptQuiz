package com.ssafy.apm.common.util;

import com.ssafy.apm.socket.dto.response.SimilarityResponseDto;
import com.ssafy.apm.gamequiz.dto.response.GameQuizDetailResponseDto;

import java.util.HashMap;
import java.util.PriorityQueue;

public class GameRoomStatus {

    public String gameCode;
    public Integer round;
    public Integer time;
    public Integer maxTime;
    public HashMap<String, String> answerWordMap;
    public HashMap<String, PriorityQueue<SimilarityResponseDto>> playerSimilarityMap;

    private static final Double similarityRate = 0.9;

    public GameRoomStatus(String gameCode, Integer round, Integer maxTime, Integer time) {
        this.gameCode = gameCode;
        this.round = round;
        this.maxTime = maxTime;
        this.time = time;
        this.playerSimilarityMap = new HashMap<>();
        this.answerWordMap = new HashMap<>();
    }

    public void initSimilarity(GameQuizDetailResponseDto quiz) {
        playerSimilarityMap.put("kor_object", new PriorityQueue<>());
        playerSimilarityMap.put("kor_subject", new PriorityQueue<>());
        playerSimilarityMap.put("kor_sub_adjective", new PriorityQueue<>());
        playerSimilarityMap.put("kor_obj_adjective", new PriorityQueue<>());

        answerWordMap.put("kor_verb", quiz.getKorVerb());
        answerWordMap.put("kor_subject", null);
        answerWordMap.put("kor_object", null);
        answerWordMap.put("kor_sub_adjective", null);
        answerWordMap.put("kor_obj_adjective", null);
    }

    public void addInitialSound(GameQuizDetailResponseDto quiz) {
        answerWordMap.computeIfAbsent("kor_object", k -> quiz.getKorObject());
        answerWordMap.computeIfAbsent("kor_subject", k -> quiz.getKorSubject());
        answerWordMap.computeIfAbsent("kor_sub_adjective", k -> quiz.getKorSubAdjective());
        answerWordMap.computeIfAbsent("kor_obj_adjective", k -> quiz.getKorObjAdjective());
    }

    public void addSimilarityAnswerToMap(String key, String value) {
        answerWordMap.put(key, value);
        playerSimilarityMap.remove(key);
    }

    public void updateSimilarityRanking(String answer, HashMap<String, Double> rateMap) {
        for (String i : rateMap.keySet()) {
            SimilarityResponseDto cur = new SimilarityResponseDto(answer, rateMap.get(i));

            if (cur.getRate() >= similarityRate) {
                addSimilarityAnswerToMap(i, answer);
            } else {
                PriorityQueue<SimilarityResponseDto> ranking = playerSimilarityMap.get(i);
                ranking.add(cur);
                if (ranking.size() > 3) ranking.poll();
            }
        }
    }

    public boolean similarityGameEnd() {
        return playerSimilarityMap.isEmpty();
    }

}
