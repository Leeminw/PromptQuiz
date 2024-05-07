package com.ssafy.apm.channel.dto.request;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ChannelChatRequestDto {

    private String uuid;
    private String nickname;
    private String content;

}
