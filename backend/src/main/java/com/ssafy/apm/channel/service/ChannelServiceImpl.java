package com.ssafy.apm.channel.service;

import com.ssafy.apm.channel.dto.request.ChannelCreateRequestDto;
import com.ssafy.apm.channel.dto.response.ChannelGetResponseDto;
import com.ssafy.apm.channel.repository.ChannelRepository;
import com.ssafy.apm.channel.repository.UserChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChannelServiceImpl implements ChannelService{

    private final ChannelRepository channelRepository;
    private final UserChannelRepository userChannelRepository;

    @Transactional
    @Override
    public void createChannel(ChannelCreateRequestDto dto) {

    }

    @Override
    public List<ChannelGetResponseDto> getChannelList() {
        return null;
    }
}
