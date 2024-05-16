package com.ssafy.apm.sdw.service;

import com.ssafy.apm.sdw.dto.SdwSimpleRequestDto;
import com.ssafy.apm.sdw.dto.SdwSimpleResponseDto;
import com.ssafy.apm.sdw.dto.SdwCustomRequestDto;
import com.ssafy.apm.sdw.dto.SdwCustomResponseDto;

public interface SdwService {

    SdwSimpleResponseDto requestSimpleStableDiffusion(SdwSimpleRequestDto requestDto);
    SdwCustomResponseDto requestCustomStableDiffusion(SdwCustomRequestDto requestDto);

}
