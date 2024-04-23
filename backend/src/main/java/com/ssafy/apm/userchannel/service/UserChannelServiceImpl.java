package com.ssafy.apm.userchannel.service;

import com.ssafy.apm.userchannel.dto.response.UserChannelUsersResponseDto;
import com.ssafy.apm.userchannel.repository.UserChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserChannelServiceImpl implements UserChannelService{

    private final UserChannelRepository userChannelRepository;
    @Override
    public List<UserChannelUsersResponseDto> getUserChannelList(Long channelId) {
        return null;
    }
}
