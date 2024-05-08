package com.ssafy.apm.channel.service;

import com.ssafy.apm.channel.domain.Channel;
import com.ssafy.apm.channel.repository.ChannelRepository;
import com.ssafy.apm.channel.exception.ChannelNotFoundException;
import com.ssafy.apm.channel.dto.response.ChannelGetResponseDto;
import com.ssafy.apm.channel.dto.request.ChannelCreateRequestDto;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;

    @Transactional
    @Override
    public ChannelGetResponseDto createChannel(ChannelCreateRequestDto dto) {
        Channel channel = dto.toEntity();
        channelRepository.save(channel);
        return new ChannelGetResponseDto(channel);
    }

    @Override
    public List<ChannelGetResponseDto> getChannelList() {
        List<Channel> channelList = channelRepository.findAll();
        return channelList.stream()
                .map(ChannelGetResponseDto::new)
                .toList();
    }

    @Override
    public ChannelGetResponseDto getChannel(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));
        return new ChannelGetResponseDto(channel);
    }

    @Override
    public ChannelGetResponseDto getChannelByCode(String code) {
        Channel channel = channelRepository.findByCode(code)
                .orElseThrow(() -> new ChannelNotFoundException("No entities exists by code"));
        return new ChannelGetResponseDto(channel);
    }

}
