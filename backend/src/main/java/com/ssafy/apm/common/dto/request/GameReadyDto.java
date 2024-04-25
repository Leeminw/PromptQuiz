package com.ssafy.apm.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameReadyDto {
    // 게임방 아이디
    private Long gameId;

    // 게임방 uuid
    private String uuid;

    // 퀴즈 라운드
    private Integer round;
}
