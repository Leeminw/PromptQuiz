package com.ssafy.apm.chat.service;

import com.ssafy.apm.channel.dto.response.ChannelChatResponseDto;
import com.ssafy.apm.chat.domain.Chat;
import com.ssafy.apm.socket.dto.request.GameChatRequestDto;
import com.ssafy.apm.channel.dto.request.ChannelChatRequestDto;
import com.ssafy.apm.socket.dto.response.GameChatResponseDto;

import java.util.List;

public interface ChatService {

    GameChatResponseDto insertGameChat(GameChatRequestDto request);

    ChannelChatResponseDto insertChannelChat(ChannelChatRequestDto request);

    List<Chat> getChatListByTimeRange(Integer hour);

}
