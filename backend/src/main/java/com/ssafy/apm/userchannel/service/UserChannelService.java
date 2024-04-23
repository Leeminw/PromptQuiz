package com.ssafy.apm.userchannel.service;

import com.ssafy.apm.user.dto.UserDetailResponseDto;

import java.util.List;

public interface UserChannelService {
    List<UserDetailResponseDto> getUserChannelList(Long channelId);
}
