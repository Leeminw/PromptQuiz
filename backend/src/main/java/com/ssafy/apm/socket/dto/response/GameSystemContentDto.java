package com.ssafy.apm.socket.dto.response;

import com.ssafy.apm.gameuser.dto.response.GameUserSimpleResponseDto;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GameSystemContentDto {

    private Integer round;
    private List<GameRoundResultResponseDto> roundList;

    public GameSystemContentDto(Integer round) {
        this.round = round;
        this.roundList = null;
    }

    public GameSystemContentDto(List<GameRoundResultResponseDto> roundList) {
        this.round = 0;
        this.roundList = roundList;
    }

}
