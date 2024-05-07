package com.ssafy.apm.channel.dto.request;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ChannelChatDto {

    private String nickname;
    private String uuid;
    private String content;
    private String createdDate;

}
