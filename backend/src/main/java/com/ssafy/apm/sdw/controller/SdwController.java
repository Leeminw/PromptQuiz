package com.ssafy.apm.sdw.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.sdw.dto.SdwSimpleRequestDto;
import com.ssafy.apm.sdw.dto.SdwSimpleResponseDto;
import com.ssafy.apm.sdw.dto.SdwCustomRequestDto;
import com.ssafy.apm.sdw.dto.SdwCustomResponseDto;
import com.ssafy.apm.sdw.service.SdwService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SdwController {

    private final SdwService sdwService;

    @PostMapping("/api/v1/sdw/txt2img/simple")
    public ResponseEntity<ResponseData<?>> requestStableDiffusion(@RequestBody SdwSimpleRequestDto requestDto) {
        SdwSimpleResponseDto responseDto = sdwService.requestSimpleStableDiffusion(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @PostMapping("/api/v1/sdw/txt2img/custom")
    public ResponseEntity<ResponseData<?>> requestStableDiffusion(@RequestBody SdwCustomRequestDto requestDto) {
        SdwCustomResponseDto responseDto = sdwService.requestCustomStableDiffusion(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

}
