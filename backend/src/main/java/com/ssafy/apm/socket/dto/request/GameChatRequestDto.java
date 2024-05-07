package com.ssafy.apm.socket.dto.request;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class GameChatRequestDto {

    private Long userId;
    private String nickname;
    private String uuid;
    private Long gameId;
    private Integer round;
    private String content;

}
