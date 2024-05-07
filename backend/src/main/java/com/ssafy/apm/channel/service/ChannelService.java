package com.ssafy.apm.channel.service;

import com.ssafy.apm.channel.dto.response.ChannelGetResponseDto;
import com.ssafy.apm.channel.dto.request.ChannelCreateRequestDto;

import java.util.List;

public interface ChannelService {

    ChannelGetResponseDto createChannel(ChannelCreateRequestDto dto);

    ChannelGetResponseDto getChannel(Long channelId);

    ChannelGetResponseDto getChannelByCode(String code);

    List<ChannelGetResponseDto> getChannelList();

}
