package com.ssafy.apm.user.service;

import com.ssafy.apm.user.dto.*;

public interface UserService {
    UserDetailResponseDto createUser(UserCreateRequestDto requestDto);
    UserDetailResponseDto readUser(Long userId);
    UserDetailResponseDto updateUser(UserUpdateRequestDto requestDto);
    UserDetailResponseDto deleteUser(Long userId);

    UserLoginResponseDto loginUser(UserLoginRequestDto requestDto);
    Boolean isExistUserName(String userName);



}
