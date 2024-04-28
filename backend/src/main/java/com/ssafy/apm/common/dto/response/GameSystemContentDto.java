package com.ssafy.apm.common.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GameSystemContentDto {
    // 게임에서 현재 라운드
    // 초기값 0
    private Integer round;

    // (라운드 결과) 유저 리스트 , null 값 일 수 있음
    private List<PlayerDto> roundList;
}
