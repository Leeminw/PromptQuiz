package com.ssafy.apm.sdw.service;

import com.ssafy.apm.sdw.dto.DottegiRequestDto;

public interface AutoCreateService {
    void autoCreateScheduler();
    String autoCreateScheduler(DottegiRequestDto requestDto);

}
