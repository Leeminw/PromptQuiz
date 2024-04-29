package com.ssafy.apm.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnterUserDto {
    String nickname;
    String userId;
    Long uuid;
}
