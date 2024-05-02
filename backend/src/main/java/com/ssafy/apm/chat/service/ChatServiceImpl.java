package com.ssafy.apm.chat.service;

import com.ssafy.apm.chat.domain.Chat;
import com.ssafy.apm.chat.repository.ChatRepository;
import com.ssafy.apm.socket.dto.request.GameChatDto;
import com.ssafy.apm.channel.dto.request.ChannelChatDto;

import java.util.List;
import java.time.Instant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
  private final ChatRepository chatRepository;

  @Override
  public void insertChannelChat(ChannelChatDto request) {
    Chat input = new Chat(Instant.now(), request.getUuid(), request.getNickname(), request.getContent());
    chatRepository.save(input);
  }

  @Override
  public void insertGameChat(GameChatDto request) {
    Chat input = new Chat(Instant.now(), request.getUuid(), request.getNickname(), request.getContent());
    chatRepository.save(input);
  }

  public List<Chat> getChatListUsingHour(Integer hour) {
    return chatRepository.findByHourDuration(hour);
  }
}
