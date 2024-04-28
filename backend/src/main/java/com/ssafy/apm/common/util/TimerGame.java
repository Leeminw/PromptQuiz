package com.ssafy.apm.common.util;

import com.ssafy.apm.common.dto.response.SimilarityResponseDto;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class TimerGame {
    public Long gameId;
    public String uuid;
    public Integer round;
    public Integer time;
    public Integer maxTime;

    public HashMap<String, PriorityQueue<SimilarityResponseDto>> simList;

    public void initSimilarity(List<String> typeList){
        // 이전 값 전부 삭제하기
        simList.clear();
        
        // 새로운 리스트 만들어주기 (String == '명사', '동사' 등 품사에 관련한 리스트)
        for(String i : typeList){
            simList.put(i, new PriorityQueue<>());
        }
    }

    public void addSimilarity(String value, Double rate){
        // 새로운 유사도 객체 생성
        SimilarityResponseDto cur = new SimilarityResponseDto(value, rate);
        
        // simList에 있는 모든 값 순회하면서 확인하기
        for(String i : simList.keySet()){

            // 각 PQ에 현재값을 넣고 size가 3이 될 때까지 줄여주기
            PriorityQueue<SimilarityResponseDto> temp = simList.get(i);
            temp.add(cur);
            while(temp.size() > 3){
                temp.poll();
            }
        }
    }

    public TimerGame(Long gameId, String uuid, Integer round, Integer maxTime, Integer time) {
        this.gameId = gameId;
        this.uuid = uuid;
        this.round = round;
        this.maxTime = maxTime;
        this.time = time;
        this.simList = new HashMap<>();
    }
}
