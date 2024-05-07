package com.ssafy.apm.socket.dto.request;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class EnterUserMessageDto {

    private String nickname;
    private Long userId;
    private String uuid;

}
