package com.ssafy.apm.socket.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GameReadyDto {

    private String gameCode;

}
