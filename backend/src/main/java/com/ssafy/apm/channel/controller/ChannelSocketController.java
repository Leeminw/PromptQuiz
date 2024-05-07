package com.ssafy.apm.channel.controller;

import com.ssafy.apm.chat.domain.Chat;
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

    @MessageMapping("/channel/chat/send")
    public void sendChannelChat(@Payload ChannelChatDto chatMessage) {
        Chat chat = chatService.insertChannelChat(chatMessage);
        chatMessage.setCreatedDate(chat.getLocalTime());

        template.convertAndSend("/ws/sub/channel?uuid=" + chatMessage
                .getUuid(), chatMessage);
    }

}
