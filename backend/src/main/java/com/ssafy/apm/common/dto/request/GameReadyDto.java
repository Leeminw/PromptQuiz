package com.ssafy.apm.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameReadyDto {
    // 게임방 아이디
    private Long uuid;

    // 퀴즈 아이디
    private Long quizId;
}
