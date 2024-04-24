package com.ssafy.apm.channel.service;

import com.ssafy.apm.channel.domain.ChannelEntity;
import com.ssafy.apm.channel.dto.request.ChannelCreateRequestDto;
import com.ssafy.apm.channel.dto.response.ChannelGetResponseDto;
import com.ssafy.apm.channel.repository.ChannelRepository;
import com.ssafy.apm.userchannel.repository.UserChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChannelServiceImpl implements ChannelService{

    private final ChannelRepository channelRepository;
    private final UserChannelRepository userChannelRepository;

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
}
