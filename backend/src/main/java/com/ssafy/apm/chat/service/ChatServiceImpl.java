package com.ssafy.apm.chat.service;

import com.ssafy.apm.chat.domain.Chat;
import com.ssafy.apm.chat.repository.ChatRepository;
import com.ssafy.apm.socket.dto.request.GameChatDto;
import com.ssafy.apm.channel.dto.request.ChannelChatDto;

import java.util.List;
import java.time.Instant;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public void insertGameChat(GameChatDto request) {
        try{
            Chat input = new Chat(Instant.now(), request.getUuid(), request.getNickname(), request.getContent());
            chatRepository.save(input);
        }catch (Exception e){
            log.debug("InsertGameChat Error : {}", e.getMessage());
        }
    }

    @Override
    public void insertChannelChat(ChannelChatDto request) {
        try{
            Chat input = new Chat(Instant.now(), request.getUuid(), request.getNickname(), request.getContent());
            chatRepository.save(input);
        }catch (Exception e){
            log.debug("InsertChannelChat Error : {}", e.getMessage());
        }
    }

    public List<Chat> getChatListByTimeRange(Integer hour) {
        return chatRepository.findByHourDuration(hour);
    }
}
