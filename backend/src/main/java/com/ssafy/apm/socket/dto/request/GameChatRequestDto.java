package com.ssafy.apm.socket.dto.request;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class GameChatRequestDto {

    private Long userId;
    private String nickname;
    private String uuid;
    private String gameCode;
    private Integer round;
    private String content;

}
