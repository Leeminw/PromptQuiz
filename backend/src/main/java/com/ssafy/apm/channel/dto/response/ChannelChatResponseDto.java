package com.ssafy.apm.channel.dto.response;

import com.ssafy.apm.chat.domain.Chat;
import com.ssafy.apm.channel.dto.request.ChannelChatRequestDto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.time.ZoneId;
import java.time.Instant;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class ChannelChatResponseDto {

    private Long userId;
    private String uuid;
    private String nickname;
    private String content;
    private String createdDate;

    public static ChannelChatResponseDto buildFromEntity(Chat chat, Long userId) {
        return ChannelChatResponseDto.builder()
                .userId(userId)
                .uuid(chat.getUuid())
                .nickname(chat.getNickname())
                .content(chat.getContent())
                .createdDate(chat.getLocalTime())
                .build();
    }

    public static ChannelChatResponseDto buildFromRequest(ChannelChatRequestDto chat) {
        return ChannelChatResponseDto.builder()
                .userId(chat.getUserId())
                .uuid(chat.getUuid())
                .nickname(chat.getNickname())
                .content(chat.getContent())
                .createdDate(getLocalTime())
                .build();
    }

    public static String getLocalTime() {
        return LocalTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString();
    }

}
