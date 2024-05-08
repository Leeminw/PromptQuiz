package com.ssafy.apm.socket.dto.response;

import com.ssafy.apm.chat.domain.Chat;
import com.ssafy.apm.socket.dto.request.GameChatRequestDto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.time.ZoneId;
import java.time.Instant;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class GameChatResponseDto {

    private Long userId;
    private String uuid;
    private String nickname;
    private String content;
    private String createdDate;

    public static GameChatResponseDto buildFromEntity(Chat chat, Long userId) {
        return GameChatResponseDto.builder()
                .userId(userId)
                .uuid(chat.getUuid())
                .nickname(chat.getNickname())
                .content(chat.getContent())
                .createdDate(chat.getLocalTime())
                .build();
    }

    public static GameChatResponseDto buildFromRequest(GameChatRequestDto chat) {
        return GameChatResponseDto.builder()
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
