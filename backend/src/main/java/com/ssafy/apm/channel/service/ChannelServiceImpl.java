package com.ssafy.apm.channel.service;

import com.ssafy.apm.channel.domain.ChannelEntity;
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
        ChannelEntity channelEntity = dto.toEntity();
        channelRepository.save(channelEntity);
        return new ChannelGetResponseDto(channelEntity);
    }

    @Override
    public List<ChannelGetResponseDto> getChannelList() {
        List<ChannelEntity> channelEntityList = channelRepository.findAll();
        return channelEntityList.stream()
                .map(ChannelGetResponseDto::new)
                .toList();
    }

    @Override
    public ChannelGetResponseDto getChannel(Long channelId) {
        ChannelEntity channelEntity = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));
        return new ChannelGetResponseDto(channelEntity);
    }

    @Override
    public ChannelGetResponseDto getChannelByCode(String code) {
        ChannelEntity channelEntity = channelRepository.findByCode(code)
                .orElseThrow(() -> new ChannelNotFoundException("No entities exists by code"));
        return new ChannelGetResponseDto(channelEntity);
    }

}
