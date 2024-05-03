package com.ssafy.apm.socket.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GameSystemResponseDto {
    // 게임의 타입 분류 -> ready, start, end, result
    private String type;

    // content는 각각 상태에 들어갈 내용
    private GameSystemContentDto content;

    // 라운드 준비 상태 response
    public static GameSystemResponseDto ready(GameSystemContentDto content) {
        return new GameSystemResponseDto("ready", content);
    }

    // 라운드 시작 상태 response
    public static GameSystemResponseDto start(GameSystemContentDto content) {
        return new GameSystemResponseDto("start", content);
    }

    // 라운드 종료 상태 response
    public static GameSystemResponseDto end(GameSystemContentDto content) {
        return new GameSystemResponseDto("end", content);
    }

    // 전체 게임 종료 response
    public static GameSystemResponseDto result(GameSystemContentDto content) {
        return new GameSystemResponseDto("result", content);
    }
}
