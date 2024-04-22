package com.ssafy.apm.user.service;

import com.ssafy.apm.user.dto.UserCreateRequestDto;
import com.ssafy.apm.user.dto.UserDetailResponseDto;
import com.ssafy.apm.user.dto.UserUpdateRequestDto;

public interface UserService {
    UserDetailResponseDto createUser(UserCreateRequestDto requestDto);
    UserDetailResponseDto readUser(Long userId);
    UserDetailResponseDto updateUser(UserUpdateRequestDto requestDto);
    UserDetailResponseDto deleteUser(Long userId);


}
