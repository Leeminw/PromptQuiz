package com.ssafy.apm.userchannel.service;

import com.ssafy.apm.userchannel.dto.response.UserChannelUsersResponseDto;

import java.util.List;

public interface UserChannelService {
    List<UserChannelUsersResponseDto> getUserChannelList(Long channelId);
}
