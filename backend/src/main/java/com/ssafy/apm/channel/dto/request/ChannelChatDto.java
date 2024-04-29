package com.ssafy.apm.channel.dto.request;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ChannelChatDto {
    // 사용자 이름
    private String nickname;

    // chanel code
    private Long uuid;

    // 메시지의 내용을 저장하기 위한 변수
    private String content;

    // 메시지 작성시간
    private String createdDate;
}
