package com.ssafy.apm.sdw.service;

import com.ssafy.apm.dottegi.dto.DottegiRequestDto;

public interface AutoCreateService {
    void autoCreateScheduler();
    String autoCreateScheduler(DottegiRequestDto requestDto);

}
