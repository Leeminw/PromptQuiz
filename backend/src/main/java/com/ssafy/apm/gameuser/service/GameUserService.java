package com.ssafy.apm.gameuser.service;

import com.ssafy.apm.gameuser.dto.response.GameUserDetailResponseDto;
import com.ssafy.apm.user.dto.UserDetailResponseDto;

import java.util.List;

public interface GameUserService {

    List<GameUserDetailResponseDto> getGameUserList(Long gameId);
}
