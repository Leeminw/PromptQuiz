package com.ssafy.apm.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameAnswerCheck {
    // 정답인지 아닌지에 대한 Boolean 값
    Boolean result;
    // 유사도 % -> 0~1 사이 값
    Double similarity;
    // 문제의 대한 타입 확인
    String type;
}
