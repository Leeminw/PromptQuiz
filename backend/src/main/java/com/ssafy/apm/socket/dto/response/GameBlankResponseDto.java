package com.ssafy.apm.socket.dto.response;

import com.ssafy.apm.common.util.GameRoomStatus;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.PriorityQueue;

@Data
@RequiredArgsConstructor
public class GameBlankResponseDto {
    private String url;
    private HashMap<String, String> answerWord;
    private HashMap<String, PriorityQueue<SimilarityResponseDto>> playerSimilarity;

    public GameBlankResponseDto(GameRoomStatus game, String url) {
        this.url = url;
        this.answerWord = game.answerWordMap;
        this.playerSimilarity = game.playerSimilarityMap;
    }
}
