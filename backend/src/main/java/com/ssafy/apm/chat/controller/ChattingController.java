package com.ssafy.apm.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RequiredArgsConstructor
@RestController
public class ChattingController {
    private final SimpMessagingTemplate template;


    @MessageMapping("/enter")
    @SendTo("/sub/chatting")
    public String sendUserEnterMessage(String userName){
        return userName + "님이 입장하셨습니다.";
    }
    @MessageMapping("/sendChat")
    @SendTo("/sub/chatting")
    public String sendTest(String message)throws Exception{

        return "converted" + message;
    }
}