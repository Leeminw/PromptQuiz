package com.ssafy.apm.userchannel.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.user.dto.UserDetailResponseDto;
import com.ssafy.apm.userchannel.service.UserChannelService;
import com.ssafy.apm.userchannel.service.UserChannelServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-channel")
@RequiredArgsConstructor
@Slf4j
public class UserChannelController {
    /*  Todo: 채널에 접속하면 (Socket 연결 되면)UserChannelEntity를 만들어 주고, channel curPlayers 하나 늘려줘야함
              채널에서 나가면 (Socket 연결 끊기면)UserChannelEntity를 삭제해 주고, curPlayers 하나 줄여줘야함
    *
    * */

    private final UserChannelService userChannelService;

//    채널 내에 접속한 유저 정보를 리턴하는 API

    @GetMapping("/userChannelList/{channelId}")
    public ResponseEntity<ResponseData<?>> getUserChannelList(@PathVariable(name = "channelId") Long channelId) {
        List<UserDetailResponseDto> response = userChannelService.getUserChannelList(channelId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }
}
