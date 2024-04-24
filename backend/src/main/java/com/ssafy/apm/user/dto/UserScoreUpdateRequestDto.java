package com.ssafy.apm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserScoreUpdateRequestDto {
    private Integer totalScore;
    private Integer teamScore;
    private Integer soloScore;
}
