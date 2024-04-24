package com.ssafy.apm.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GameSystemResponseDto {
    // 게임의 타입 분류 -> ready, start, end, result
    private String type;

    // content는 각각 상태에 들어갈 내용
    // GameChat or GameSystemContentDto
    private Object content;

    // 라운드 및 게임 진행중 타이머
    public static GameSystemResponseDto timer(Integer time) {
        return new GameSystemResponseDto("timer", time);
    }

    // 라운드 준비 상태 response
    public static GameSystemResponseDto ready(Object content) {
        return new GameSystemResponseDto("ready", content);
    }

    // 라운드 시작 상태 response
    public static GameSystemResponseDto start(Object content) {
        return new GameSystemResponseDto("start", content);
    }

    // 라운드 종료 상태 response
    public static GameSystemResponseDto end(Object content) {
        return new GameSystemResponseDto("end", content);
    }

    // 전체 게임 종료 response
    public static GameSystemResponseDto result(Object content) {
        return new GameSystemResponseDto("result", content);
    }
}
