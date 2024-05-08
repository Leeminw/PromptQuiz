package com.ssafy.apm.common.util;

import com.ssafy.apm.socket.dto.response.SimilarityResponseDto;

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

    public GameRoomStatus(String gameCode, String uuid, Integer round, Integer maxTime, Integer time) {
        this.gameCode = gameCode;
        this.uuid = uuid;
        this.round = round;
        this.maxTime = maxTime;
        this.time = time;
        this.playerSimilarityMap = new HashMap<>();
        this.answerWordMap = new HashMap<>();
    }

    public void initSimilarity(HashMap<String, String> promptMap) {
        playerSimilarityMap.put("kor_object", new PriorityQueue<>());
        playerSimilarityMap.put("kor_subject", new PriorityQueue<>());
        playerSimilarityMap.put("kor_sub_adjective", new PriorityQueue<>());
        playerSimilarityMap.put("kor_obj_adjective", new PriorityQueue<>());

        answerWordMap.putAll(promptMap);
    }

    public void addSimilarityAnswerToMap(String key, String value) {
        answerWordMap.put(key, value);
        playerSimilarityMap.remove(key);
    }

    public void addSimilarityToMap(String answer, HashMap<String, Double> rateMap) {

        for (String i : rateMap.keySet()) {
            SimilarityResponseDto cur = new SimilarityResponseDto(answer, rateMap.get(i));

            if (cur.getRate() >= similarityRate) {
                addSimilarityAnswerToMap(i, answer);
            } else {
                PriorityQueue<SimilarityResponseDto> temp = playerSimilarityMap.get(i);
                temp.add(cur);

                if (temp.size() > 3) {
                    temp.poll();
                }
            }
        }
    }

    public boolean similarityGameEnd() {
        return playerSimilarityMap.isEmpty();
    }

}
