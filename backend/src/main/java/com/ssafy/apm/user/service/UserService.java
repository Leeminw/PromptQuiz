package com.ssafy.apm.user.service;

import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.dto.*;

public interface UserService {
    User loadUser();
    UserDetailResponseDto createUser(UserCreateRequestDto requestDto);
    UserDetailResponseDto readUser();
//    UserDetailResponseDto updateUser(UserUpdateRequestDto requestDto);
//    UserDetailResponseDto deleteUser(Long userId);

    UserLoginResponseDto loginUser(UserLoginRequestDto requestDto);
    Boolean isExistUserName(String userName);
    // 프로필 수정
    UserDetailResponseDto updateProfile(String profileUrl);
    // 상태메시지 수정
    UserDetailResponseDto updateStatusMessage(String message);
    // 점수 수정
    UserDetailResponseDto updateUserScore(UserScoreUpdateRequestDto requestDto);



}
