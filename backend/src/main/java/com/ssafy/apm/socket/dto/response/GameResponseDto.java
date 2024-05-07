package com.ssafy.apm.socket.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GameResponseDto {

    private String tag;
    private Object data;
}
