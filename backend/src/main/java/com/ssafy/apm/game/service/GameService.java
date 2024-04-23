package com.ssafy.apm.game.service;

import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.response.GameGetResponseDto;

import java.util.List;

public interface GameService {
    void createGame(GameCreateRequestDto gameCreateRequestDto);

    List<GameGetResponseDto> getGameList(Long channelId);

    GameGetResponseDto getGameInfo(Long gameId);
}
