package com.ssafy.apm.socket.dto.response;

import com.ssafy.apm.common.util.GameRoomStatus;
import lombok.Data;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.PriorityQueue;

@Data
@Builder
@RequiredArgsConstructor
public class GameBlankResponseDto {
    private HashMap<String, PriorityQueue<SimilarityResponseDto>> playerSimilarity;
    private HashMap<String, String> answerWord;

    public GameBlankResponseDto(GameRoomStatus game){
        this.playerSimilarity = game.playerSimilarityMap;
        this.answerWord = game.answerWordMap;
    }
}
