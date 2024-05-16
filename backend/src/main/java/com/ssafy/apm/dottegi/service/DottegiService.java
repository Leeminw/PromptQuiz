package com.ssafy.apm.dottegi.service;

import com.ssafy.apm.dottegi.dto.DottegiResponseDto;

public interface DottegiService {

    DottegiResponseDto findLastUpdatedPayload();
    void processMessageStyle(String message);
    void processMessageSubject(String message);
    void processMessageObject(String message);
    void processMessageVerb(String message);
    void processMessageSubAdjective(String message);
    void processMessageObjAdjective(String message);
}
