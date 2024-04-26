package com.ssafy.apm.common.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class GameTimerResponseDto {
    Integer time;
    Integer round;
}
