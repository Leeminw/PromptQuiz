package com.ssafy.apm.sdw.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.dottegi.dto.DottegiRequestDto;
import com.ssafy.apm.dottegi.dto.DottegiResponseDto;
import com.ssafy.apm.sdw.dto.SdwRequestDto;
import com.ssafy.apm.sdw.dto.SdwResponseDto;
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

    @PostMapping("/api/v1/sdw/txt2img")
    public ResponseEntity<ResponseData<?>> requestStableDiffusion(@RequestBody SdwRequestDto requestDto) {
        SdwResponseDto responseDto = sdwService.requestStableDiffusion(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @PostMapping("/api/v1/dottegi/txt2img")
    public ResponseEntity<ResponseData<?>> requestStableDiffusion(@RequestBody DottegiRequestDto requestDto) {
        DottegiResponseDto responseDto = sdwService.createDottegiTxt2Img(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

}
