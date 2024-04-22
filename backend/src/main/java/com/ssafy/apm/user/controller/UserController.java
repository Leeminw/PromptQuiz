package com.ssafy.apm.user.controller;

import com.ssafy.apm.user.dto.UserCreateRequestDto;
import com.ssafy.apm.user.dto.UserDetailResponseDto;
import com.ssafy.apm.user.dto.UserLoginRequestDto;
import com.ssafy.apm.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequestDto requestDto){
        UserDetailResponseDto responseDto = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequestDto requestDto){
        log.debug("controller : {}",requestDto.toString() );
        UserDetailResponseDto responseDto = userService.loginUser(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
