package com.ssafy.apm.userchannel.service;

import com.ssafy.apm.user.dto.UserDetailResponseDto;
import com.ssafy.apm.userchannel.dto.response.UserChannelGetResponseDto;

import java.util.List;

public interface UserChannelService {
    List<UserDetailResponseDto> getUserChannelList(Long channelId);

    UserChannelGetResponseDto enterUserChannel(Long channelId);
    Long deleteExitUserChannel();

    Long deleteExitUserChannelByChannelCodeAndUserId(Long userId, String channelCode);
}
