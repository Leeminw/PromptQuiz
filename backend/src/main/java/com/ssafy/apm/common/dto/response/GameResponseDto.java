package com.ssafy.apm.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GameResponseDto {
    // 소켓 메세지 태그를 나타냅니다 (game, lobby, chat)
    private String tag;

    // data에는 내용에 해당하는 response dto가 들어갑니다.
    private Object data;
}
