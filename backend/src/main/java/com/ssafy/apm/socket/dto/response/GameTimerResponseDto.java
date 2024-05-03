package com.ssafy.apm.socket.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class GameTimerResponseDto {
    // 현재 시간 정보
    private Integer time;
    // 현재 라운드
    private Integer round;
}
