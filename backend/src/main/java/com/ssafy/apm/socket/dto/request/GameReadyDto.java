package com.ssafy.apm.socket.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameReadyDto {

    private String gameCode;

}
