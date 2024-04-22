package com.ssafy.apm.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatDto {

    // chanel code
    private String uuid;

    // 메시지의 내용을 저장하기 위한 변수
    private String content;

    // 사용자 이름
    private String nickname;

    // 메시지 작성시간
    private String createdDate;

    // 메시지 수정 시간
    private String updateDate;
}
