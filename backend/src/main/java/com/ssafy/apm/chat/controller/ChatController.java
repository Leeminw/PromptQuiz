package com.ssafy.apm.chat.controller;

import com.ssafy.apm.chat.service.ChatService;
import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.channel.dto.request.ChannelChatRequestDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/v1")
public class ChatController {

    private final ChatService service;

    @GetMapping("/readUsingHour/{hour}")
    public ResponseEntity<?> readTest(@PathVariable Integer hour) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("채팅 저장 완료", service.getChatListByTimeRange(hour)));
    }
}
