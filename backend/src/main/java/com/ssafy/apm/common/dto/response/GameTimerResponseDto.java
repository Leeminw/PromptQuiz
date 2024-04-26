package com.ssafy.apm.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameTimerResponseDto {
    Integer time;
    Integer round;
}
