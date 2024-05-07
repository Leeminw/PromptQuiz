package com.ssafy.apm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRankingResponseDto {
    private List<UserDetailResponseDto> teamRanking;
    private List<UserDetailResponseDto> soloRanking;
    private List<UserDetailResponseDto> totalRanking;

}
