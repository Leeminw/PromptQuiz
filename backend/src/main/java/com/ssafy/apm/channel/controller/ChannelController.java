package com.ssafy.apm.channel.controller;

import com.ssafy.apm.channel.dto.request.ChannelCreateRequestDto;
import com.ssafy.apm.channel.dto.response.ChannelGetResponseDto;
import com.ssafy.apm.channel.service.ChannelService;
import com.ssafy.apm.channel.service.ChannelServiceImpl;
import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.response.GameGetResponseDto;
import com.ssafy.apm.game.service.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/channel")
@RequiredArgsConstructor
@Slf4j
public class ChannelController {
    private final ChannelService channelService;

    //    채널 등록
    @PostMapping("")
    public ResponseEntity<ResponseData<?>> createChannel(@RequestBody ChannelCreateRequestDto channelCreateRequestDto) {
        ChannelGetResponseDto response = channelService.createChannel(channelCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success("채널 생성 완료", response));
    }

    //    채널 목록 조회
    @GetMapping("/channelList")
    public ResponseEntity<ResponseData<?>> getChannelList() {
        List<ChannelGetResponseDto> response = channelService.getChannelList();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("채널 리스트 조회 완료", response));
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<ResponseData<?>> getChannel(@PathVariable("channelId") Long channelId) {
        ChannelGetResponseDto response = channelService.getChannel(channelId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("채널 조회 완료", response));
    }
}
