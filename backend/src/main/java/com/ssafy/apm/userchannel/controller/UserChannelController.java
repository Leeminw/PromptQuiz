package com.ssafy.apm.userchannel.controller;

import com.ssafy.apm.channel.dto.response.ChannelGetResponseDto;
import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.userchannel.dto.response.UserChannelUsersResponseDto;
import com.ssafy.apm.userchannel.service.UserChannelServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-channel")
@RequiredArgsConstructor
@Slf4j
public class UserChannelController {

    private final UserChannelServiceImpl userChannelService;
    @GetMapping("/getUserChannelList")
    public ResponseEntity<ResponseData<?>> getUserChannelList(@RequestParam Long channelId) {
        List<UserChannelUsersResponseDto> dtoList = userChannelService.getUserChannelList(channelId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(dtoList));
    }
}
