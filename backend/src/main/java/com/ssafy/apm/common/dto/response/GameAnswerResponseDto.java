package com.ssafy.apm.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameAnswerResponseDto {
    Boolean result;
    Double similarity;
}
