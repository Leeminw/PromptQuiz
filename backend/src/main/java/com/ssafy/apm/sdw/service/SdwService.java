package com.ssafy.apm.sdw.service;

import com.ssafy.apm.sdw.dto.DottegiRequestDto;
import com.ssafy.apm.sdw.dto.DottegiResponseDto;
import com.ssafy.apm.sdw.dto.SdwRequestDto;
import com.ssafy.apm.sdw.dto.SdwResponseDto;

public interface SdwService {
    SdwResponseDto requestStableDiffusion(SdwRequestDto requestDto);
    DottegiResponseDto createDottegiTxt2Img(DottegiRequestDto requestDto);

}