package com.ssafy.apm.socket.dto.request;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class EnterUserDto {
    Long userId;
    String uuid;
    String nickname;
}
