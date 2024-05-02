package com.ssafy.apm.chat.service;

import com.ssafy.apm.channel.dto.request.ChannelChatDto;
import com.ssafy.apm.chat.domain.Chat;
import com.ssafy.apm.socket.dto.request.GameChatDto;

import java.util.List;

public interface ChatService {

  Chat insertGameChat(GameChatDto request);

  Chat insertChannelChat(ChannelChatDto request);

  List<Chat> getChatListUsingHour(Integer hour);
}
