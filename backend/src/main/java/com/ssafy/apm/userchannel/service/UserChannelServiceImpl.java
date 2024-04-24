package com.ssafy.apm.userchannel.service;

import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.dto.UserDetailResponseDto;
import com.ssafy.apm.user.repository.UserRepository;
import com.ssafy.apm.userchannel.domain.UserChannelEntity;
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
    private final UserRepository userRepository;
    @Override
    public List<UserDetailResponseDto> getUserChannelList(Long channelId) {
        List<UserChannelEntity> userChannelEntityList = userChannelRepository.findAllByChannelId(channelId);
        // UserChannelEntity 리스트에서 userId들을 추출합니다.
        List<Long> userIds = userChannelEntityList.stream()
                .map(UserChannelEntity::getUserId)
                .toList();

        // 추출한 userId들로 User 엔티티들의 리스트를 가져옵니다.
        List<User> userList = userRepository.findAllById(userIds);

        return userList.stream()
                .map(UserDetailResponseDto::new)
                .toList();
    }
}
