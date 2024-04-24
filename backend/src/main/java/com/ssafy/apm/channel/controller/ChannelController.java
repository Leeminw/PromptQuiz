package com.ssafy.apm.channel.controller;

import com.ssafy.apm.channel.dto.request.ChannelCreateRequestDto;
import com.ssafy.apm.channel.dto.response.ChannelGetResponseDto;
import com.ssafy.apm.channel.service.ChannelServiceImpl;
import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.response.GameGetResponseDto;
import com.ssafy.apm.game.service.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/channel")
@RequiredArgsConstructor
@Slf4j
public class ChannelController {
    private final ChannelServiceImpl channelService;

//    채널 등록
    @PostMapping()
    public ResponseEntity<ResponseData<?>> createChannel(@RequestBody ChannelCreateRequestDto channelCreateRequestDto) {
        channelService.createChannel(channelCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success());
    }

//    채널 목록 조회
    @GetMapping("/getChannelList")
    public ResponseEntity<ResponseData<?>> getChannelList() {
        List<ChannelGetResponseDto> dtoList = channelService.getChannelList();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(dtoList));
    }
//    Todo: 채널 삭제 API 마저 구현할 것( 필요한가? )
    @DeleteMapping()
    public ResponseEntity<ResponseData<?>> deleteGame() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success());
    }
}
