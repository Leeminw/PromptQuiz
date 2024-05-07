package com.ssafy.apm.socket.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class PlayerDto {

    private Long userId;
    private Integer score;
    private Boolean correct;
}
