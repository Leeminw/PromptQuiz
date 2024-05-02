package com.ssafy.apm.socket.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class PlayerDto {
    // player의 아이디
    private Long userId;

    // 해당 player의 점수
    private Integer score;

    // 현재 라운드 맞았는지, 틀렸는지
    private Boolean correct;
}
