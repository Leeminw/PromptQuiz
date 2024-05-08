package com.ssafy.apm.socket.dto.response;

import com.ssafy.apm.common.util.GameRoomStatus;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.PriorityQueue;

@Data
@RequiredArgsConstructor
public class GameBlankResponseDto {
    private HashMap<String, PriorityQueue<SimilarityResponseDto>> playerSimilarity;
    private HashMap<String, String> answerWord;

    public GameBlankResponseDto(GameRoomStatus game){
        this.playerSimilarity = game.playerSimilarityMap;
        this.answerWord = game.answerWordMap;
    }
}
