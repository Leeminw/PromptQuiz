package com.ssafy.apm.userchannel.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.user.dto.UserDetailResponseDto;
import com.ssafy.apm.userchannel.dto.response.UserChannelGetResponseDto;
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

    private final UserChannelService userChannelService;

    @GetMapping("/userChannelList/{channelId}")
    public ResponseEntity<ResponseData<?>> getUserChannelList(@PathVariable(name = "channelId") Long channelId) {
        List<UserDetailResponseDto> response = userChannelService.getUserChannelList(channelId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }
    @PostMapping("/{channelId}")
    public ResponseEntity<ResponseData<?>> enterUserChannel(@PathVariable(name = "channelId") Long channelId) {
        UserChannelGetResponseDto response = userChannelService.enterUserChannel(channelId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }
    @PostMapping("/code/{code}")
    public ResponseEntity<ResponseData<?>> enterUserChannelByCode(@PathVariable(name = "code") String code) {
        UserChannelGetResponseDto response = userChannelService.enterUserChannelByCode(code);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }
    @DeleteMapping("")
    public ResponseEntity<ResponseData<?>> deleteExitUserChannel() {
        Long response = userChannelService.deleteExitUserChannel();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }
    @DeleteMapping("/{userId}/{code}")
    public ResponseEntity<ResponseData<?>> deleteExitUserChannelByUserIdAndCode(
            @PathVariable(name = "userId") Long userId, @PathVariable(name = "code") String code) {
        Long response = userChannelService.deleteExitUserChannelByUserIdAndCode(userId, code);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }
}
