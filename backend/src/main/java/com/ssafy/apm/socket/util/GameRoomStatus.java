package com.ssafy.apm.socket.util;

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
        // 이전 값 전부 삭제하기
        playerSimilarityMap.clear();

        // 새로운 리스트 만들어주기 (String == '명사', '동사' 등 품사에 관련한 리스트)
        for (String i : promptMap.keySet()) {
            if(promptMap.get(i) == null){
                playerSimilarityMap.put(i, new PriorityQueue<>());
            }
        }

        // 플레이어에게 표시할 값은 String이 들어가 있고 맞춰야 하는 값은 null을 가진다.
        answerWordMap.putAll(promptMap);
    }

    public boolean similarityGameEnd(){
        return playerSimilarityMap.isEmpty();
    }

    public void addSimilarityAnswerToMap(String key, String value){
        // 새로운 정답이 나왔을 경우 정답 Map에 저장하고 유사도 목록 삭제하기
        answerWordMap.put(key, value);
        playerSimilarityMap.remove(key);
    }

    public void addSimilarityToMap(String answer, HashMap<String, Double> rateMap) {
        // rateMap에 있는 모든 품사 순회하면서 확인하기
        for (String i : rateMap.keySet()) {
            // 새로운 유사도 객체 생성
            SimilarityResponseDto cur = new SimilarityResponseDto(answer, rateMap.get(i));
            
            // 현재 유사도가 90% 이상이라면 정답처리
            if(cur.getRate() >= 0.9){
                addSimilarityAnswerToMap(i,answer);
            }else{
                // 각 PQ에 현재값을 넣고 size가 3이 될 때까지 줄여주기
                PriorityQueue<SimilarityResponseDto> temp = playerSimilarityMap.get(i);
                temp.add(cur);

                // 자기가 넣은 값에 대해서만 지우기
                if(temp.size() > 3) {
                    temp.poll();
                }
            }
        }
    }
}
