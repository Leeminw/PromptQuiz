package com.ssafy.apm.user.service;

import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.dto.UserCreateRequestDto;
import com.ssafy.apm.user.dto.UserDetailResponseDto;
import com.ssafy.apm.user.dto.UserUpdateRequestDto;
import com.ssafy.apm.user.exceptions.UserNotFoundException;
import com.ssafy.apm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public UserDetailResponseDto createUser(UserCreateRequestDto requestDto) {
        User user = requestDto.toEntity();
        userRepository.save(user);
        return new UserDetailResponseDto(user);
    }

    @Override
    public UserDetailResponseDto readUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return new UserDetailResponseDto(user);
    }

    @Override
    public UserDetailResponseDto updateUser(UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(requestDto.getUserId()));
        // user 업데이트 코드 작성.
        userRepository.save(user);
        return new UserDetailResponseDto(user);
    }

    @Override
    public UserDetailResponseDto deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
        return new UserDetailResponseDto(user);
    }
}
