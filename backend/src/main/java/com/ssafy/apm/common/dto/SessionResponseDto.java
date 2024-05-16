package com.ssafy.apm.common.dto;

import com.ssafy.apm.common.domain.Session;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SessionResponseDto {
    private String sessionId;
    private Long userId;
    private String uuid;
    private Integer type;

    public SessionResponseDto(Session session){
        this.sessionId = session.getSessionId();
        this.userId = session.getUserId();
        this.uuid = session.getUuid();
        this.type = session.getType();
    }
}
