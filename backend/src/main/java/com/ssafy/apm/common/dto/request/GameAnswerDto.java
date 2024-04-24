package com.ssafy.apm.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameAnswerDto {
    // 사용자 아이디
    private String userId;

    // 게임방 아이디
    private Long gameId;

    // 퀴즈 아이디
    private Long quizId;

    // 플레이어가 선택한 답안
    private String answer;
}
