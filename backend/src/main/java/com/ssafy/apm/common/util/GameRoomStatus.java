package com.ssafy.apm.common.util;

import com.ssafy.apm.socket.dto.response.SimilarityResponseDto;

import java.util.HashMap;
import java.util.PriorityQueue;

public class GameRoomStatus {

    public Long gameId;
    public String uuid;
    public Integer round;
    public Integer time;
    public Integer maxTime;
    public HashMap<String, String> answerWordMap;
    public HashMap<String, PriorityQueue<SimilarityResponseDto>> playerSimilarityMap;

    private static final Double similarityRate = 0.9;

    public GameRoomStatus(Long gameId, String uuid, Integer round, Integer maxTime, Integer time) {
        this.gameId = gameId;
        this.uuid = uuid;
        this.round = round;
        this.maxTime = maxTime;
        this.time = time;
        this.playerSimilarityMap = new HashMap<>();
        this.answerWordMap = new HashMap<>();
    }

    public void initSimilarity(HashMap<String, String> promptMap) {

        playerSimilarityMap.clear();

        for (String i : promptMap.keySet()) {
            if (promptMap.get(i) == null) {
                playerSimilarityMap.put(i, new PriorityQueue<>());
            }
        }

        answerWordMap.putAll(promptMap);
    }

    public void addSimilarityAnswerToMap(String key, String value) {
        // 새로운 정답이 나왔을 경우 정답 Map에 저장하고 유사도 목록 삭제하기
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
