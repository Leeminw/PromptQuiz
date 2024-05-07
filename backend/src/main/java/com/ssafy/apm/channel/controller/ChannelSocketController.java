package com.ssafy.apm.channel.controller;

import com.ssafy.apm.chat.service.ChatService;
import com.ssafy.apm.channel.dto.request.ChannelChatDto;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.POST}, maxAge = 6000)
public class ChannelSocketController {
    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    // -------------------- 채팅 관련 브로커 --------------------
    // 채널에서 보내는 메세지
    @MessageMapping("/channel/chat/send")
    public void sendChannelChat(@Payload ChannelChatDto chatMessage) {
        ChannelChatDto chat = chatService.insertChannelChat(chatMessage);

        template.convertAndSend("/ws/sub/channel?uuid=" + chat
                .getUuid(), chat);
    }
}
