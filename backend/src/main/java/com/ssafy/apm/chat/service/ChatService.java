package com.ssafy.apm.chat.service;

import com.ssafy.apm.chat.domain.Chat;
import com.ssafy.apm.socket.dto.request.GameChatDto;
import com.ssafy.apm.channel.dto.request.ChannelChatDto;

import java.util.List;

public interface ChatService {
    Chat insertGameChat(GameChatDto request);

    Chat insertChannelChat(ChannelChatDto request);

    List<Chat> getChatListByTimeRange(Integer hour);
}
