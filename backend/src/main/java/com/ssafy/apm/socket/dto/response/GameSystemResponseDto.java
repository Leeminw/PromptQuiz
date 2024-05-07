package com.ssafy.apm.socket.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GameSystemResponseDto {

    private String type;
    private GameSystemContentDto content;

    public static GameSystemResponseDto ready(GameSystemContentDto content) {
        return new GameSystemResponseDto("ready", content);
    }

    public static GameSystemResponseDto start(GameSystemContentDto content) {
        return new GameSystemResponseDto("start", content);
    }

    public static GameSystemResponseDto end(GameSystemContentDto content) {
        return new GameSystemResponseDto("end", content);
    }

    public static GameSystemResponseDto result(GameSystemContentDto content) {
        return new GameSystemResponseDto("result", content);
    }

}
