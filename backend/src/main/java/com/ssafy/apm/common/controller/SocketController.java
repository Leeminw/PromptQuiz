package com.ssafy.apm.common.controller;

import com.ssafy.apm.common.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
public class SocketController {

    private final SimpMessagingTemplate template;

    // 처음 채팅방에 들어왔을 때 보내는 메세지
    // (OO님이 채팅방에 참여하셨습니다)
    @MessageMapping("/chat/enter")
    public void enterMessage(@Payload ChatDto chatMessage) {
        template.convertAndSend("/sub/chat/room/"+chatMessage
                .getUuid(), chatMessage);
    }

    // 메세지로 들어온 정보에 대해서 입력을 받고 해당 room을 구독중인 구독자에게 정보를 뿌리기
    @MessageMapping("/chat/send")
    public void sendMessage(@Payload ChatDto chatMessage) {
        template.convertAndSend("/sub/chat/room/"+chatMessage.getUuid(), chatMessage);
    }
}
