package com.ssafy.apm.socket.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class GameTimerResponseDto {
    private Integer time;
    private Integer round;
}
