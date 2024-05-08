package com.ssafy.apm.userchannel.service;

import com.ssafy.apm.user.dto.UserDetailResponseDto;
import com.ssafy.apm.userchannel.dto.response.UserChannelGetResponseDto;

import java.util.List;

public interface UserChannelService {
    List<UserDetailResponseDto> getUserChannelList(Long channelId);

    UserChannelGetResponseDto enterUserChannel(Long channelId);
    UserChannelGetResponseDto enterUserChannelByCode(String code);
    Long deleteExitUserChannel();

    Long deleteExitUserChannelByUserIdAndCode(Long userId, String code);
}
