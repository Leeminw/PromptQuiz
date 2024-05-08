package com.ssafy.apm.chat.service;

import com.ssafy.apm.chat.domain.Chat;
import com.ssafy.apm.chat.repository.ChatRepository;
import com.ssafy.apm.socket.dto.request.GameChatRequestDto;
import com.ssafy.apm.socket.dto.response.GameChatResponseDto;
import com.ssafy.apm.channel.dto.request.ChannelChatRequestDto;
import com.ssafy.apm.channel.dto.response.ChannelChatResponseDto;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.time.Instant;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public GameChatResponseDto insertGameChat(GameChatRequestDto request) {
        try {
            Chat chat = new Chat(Instant.now(), request);
            chatRepository.save(chat);
            return GameChatResponseDto.buildFromEntity(chat, request.getUserId());
        } catch (Exception e) {
            log.debug("InsertGameChat Error : {}", e.getMessage());
        }
        return GameChatResponseDto.buildFromRequest(request);
    }

    @Override
    public ChannelChatResponseDto insertChannelChat(ChannelChatRequestDto request) {
        try {
            Chat chat = new Chat(Instant.now(), request);
            chatRepository.save(chat);
            return ChannelChatResponseDto.buildFromEntity(chat, request.getUserId());
        } catch (Exception e) {
            log.debug("InsertChannelChat Error : {}", e.getMessage());
        }
        return ChannelChatResponseDto.buildFromRequest(request);
    }

    public List<Chat> getChatListByTimeRange(Integer hour) {
        return chatRepository.findByHourDuration(hour);
    }

}
