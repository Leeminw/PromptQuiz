package com.ssafy.apm.chat.controller;

import com.ssafy.apm.chat.service.ChatService;
import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.channel.dto.request.ChannelChatDto;

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

  @GetMapping("/write/{content}")
  public ResponseEntity<?> writeTest(@PathVariable String content){
    // 컨트롤러는 사용하지 않으니 일단 channel 채팅으로 가정하고 저장
    ChannelChatDto channel = new ChannelChatDto("testNickName", 123456L, content,null);
    service.insertChannelChat(channel);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("채팅 저장 완료", "OK"));
  }

  @GetMapping("/readUsingHour/{hour}")
  public ResponseEntity<?> readTest(@PathVariable Integer hour){
    return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("채팅 저장 완료", service.getChatListUsingHour(hour)));
  }
}
