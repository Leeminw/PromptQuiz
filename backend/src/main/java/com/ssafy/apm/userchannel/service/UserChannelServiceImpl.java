package com.ssafy.apm.userchannel.service;

import com.ssafy.apm.channel.domain.ChannelEntity;
import com.ssafy.apm.channel.repository.ChannelRepository;
import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.dto.UserDetailResponseDto;
import com.ssafy.apm.user.repository.UserRepository;
import com.ssafy.apm.user.service.UserService;
import com.ssafy.apm.userchannel.domain.UserChannelEntity;
import com.ssafy.apm.userchannel.dto.response.UserChannelGetResponseDto;
import com.ssafy.apm.userchannel.repository.UserChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserChannelServiceImpl implements UserChannelService {

    private final UserChannelRepository userChannelRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final UserService userService;

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

    @Override
    @Transactional
    public UserChannelGetResponseDto enterUserChannel(Long channelId) {
        User user = userService.loadUser();
        UserChannelEntity entity = UserChannelEntity.builder()
                .userId(user.getId())
                .channelId(channelId)
                .build();

        entity = userChannelRepository.save(entity);
        return new UserChannelGetResponseDto(entity);

    }

    @Override
    @Transactional
    public Long deleteExitUserChannel() {
        User user = userService.loadUser();
        UserChannelEntity entity = userChannelRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException("현재 채널에 접속중이지 않습니다."));
        Long response = entity.getId();
        userChannelRepository.delete(entity);
        return response;
    }

    @Override
    @Transactional
    public Long deleteExitUserChannelByChannelCodeAndUserId(Long userId, String channelCode) {
        ChannelEntity channelEntity = channelRepository.findByCode(channelCode)
                .orElseThrow(() -> new NoSuchElementException("채널 코드에 해당하는 채널이 존재하지 않습니다."));
        UserChannelEntity entity = userChannelRepository.findByUserIdAndChannelId(userId, channelEntity.getId())
                .orElseThrow(() -> new NoSuchElementException("유저의 Id와 채널 코드에 해당하는 채널 접속자 정보가 없습니다."));

        Long response = entity.getId();
        userChannelRepository.delete(entity);
        return response;
    }
}
