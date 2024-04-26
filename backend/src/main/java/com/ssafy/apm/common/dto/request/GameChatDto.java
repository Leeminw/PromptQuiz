package com.ssafy.apm.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameChatDto {
    // 사용자 아이디
    private String userId;

    // 게임방 코드
    private Long uuid;

    // 게임방 아이디
    private Long gameId;

    // 현재 라운드
    private Integer round;

    // 메시지의 내용을 저장하기 위한 변수
    private String content;

    // 메시지 작성시간
    private String createdDate;
}
