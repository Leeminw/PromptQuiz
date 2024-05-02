package com.ssafy.apm.socket.dto.request;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class GameReadyDto {
    // 게임방 아이디
    private Long gameId;

    // 게임방 uuid
    private String uuid;

}