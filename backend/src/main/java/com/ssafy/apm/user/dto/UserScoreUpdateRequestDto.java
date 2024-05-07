package com.ssafy.apm.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserScoreUpdateRequestDto {

    private Integer totalScore;
    private Integer teamScore;
    private Integer soloScore;

}
