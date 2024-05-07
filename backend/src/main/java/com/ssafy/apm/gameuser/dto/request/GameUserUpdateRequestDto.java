package com.ssafy.apm.gameuser.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameUserUpdateRequestDto {
    private String code;
    private String gameCode;
    private Long userId;
    private String team;
    private Integer score;
    private Boolean isHost;
}
