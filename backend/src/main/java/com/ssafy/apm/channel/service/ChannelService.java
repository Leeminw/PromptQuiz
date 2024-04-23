package com.ssafy.apm.channel.service;

import com.ssafy.apm.channel.dto.request.ChannelCreateRequestDto;
import com.ssafy.apm.channel.dto.response.ChannelGetResponseDto;

import java.util.List;

public interface ChannelService {
    void createChannel(ChannelCreateRequestDto dto);
    List<ChannelGetResponseDto> getChannelList();
}