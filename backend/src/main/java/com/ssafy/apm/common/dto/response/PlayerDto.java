package com.ssafy.apm.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerDto {
    // player의 아이디
    String userId;
    
    // 해당 player의 점수
    Integer score;
    
    // 현재 라운드 맞았는지, 틀렸는지
    Boolean correct;
}
