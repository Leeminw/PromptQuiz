package com.ssafy.apm.game.service;

import com.ssafy.apm.game.dto.request.GameCreateRequestDto;

public interface GameService {
    void createGame(GameCreateRequestDto gameCreateRequestDto);
}
