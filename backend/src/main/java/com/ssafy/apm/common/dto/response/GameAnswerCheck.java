package com.ssafy.apm.common.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameAnswerCheck {
    // 문제의 대한 타입 확인
    Integer type;
    // 정답인지 아닌지에 대한 Boolean 값
    Boolean result;
    // 유사도 % -> 0~1 사이 값
    HashMap<String, Double> similarity;
}
