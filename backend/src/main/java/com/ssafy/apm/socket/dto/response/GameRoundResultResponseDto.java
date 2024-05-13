package com.ssafy.apm.socket.dto.response;

import com.ssafy.apm.gameuser.dto.response.GameUserSimpleResponseDto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GameRoundResultResponseDto {
    private String gameCode;
    private Long userId;
    private Integer score;
    private Boolean isCorrect;

    public static List<GameRoundResultResponseDto> build(List<GameUserSimpleResponseDto> input, Long correctId) {
        List<GameRoundResultResponseDto> list = new ArrayList<>();
        for (GameUserSimpleResponseDto i : input) {
            list.add(new GameRoundResultResponseDto(
                    i.getGameCode(),
                    i.getUserId(),
                    i.getScore(),
                    i.getUserId().equals(correctId)
            ));
        }
        return list;
    }
}
