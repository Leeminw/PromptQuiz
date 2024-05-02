package com.ssafy.apm.chat;

import com.ssafy.apm.chat.service.ChatService;
import com.ssafy.apm.common.domain.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/v1")
@RequiredArgsConstructor
public class TestChatController {
  private final ChatService service;

  @GetMapping("/write/{content}")
  public ResponseEntity<?> writeTest(@PathVariable String content){
    service.getChatList();

    return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("채팅 저장 완료", "OK"));
  }
}
