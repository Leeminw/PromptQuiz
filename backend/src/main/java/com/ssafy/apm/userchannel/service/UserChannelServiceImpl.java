package com.ssafy.apm.userchannel.service;

import com.ssafy.apm.channel.domain.ChannelEntity;
import com.ssafy.apm.channel.exception.ChannelNotFoundException;
import com.ssafy.apm.channel.repository.ChannelRepository;
import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.dto.UserDetailResponseDto;
import com.ssafy.apm.user.repository.UserRepository;
import com.ssafy.apm.user.service.UserService;
import com.ssafy.apm.userchannel.domain.UserChannelEntity;
import com.ssafy.apm.userchannel.dto.response.UserChannelGetResponseDto;
import com.ssafy.apm.userchannel.exception.UserChannelNotFoundException;
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
        List<Long> userIds = userChannelEntityList.stream()
                .map(UserChannelEntity::getUserId)
                .toList();
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

        ChannelEntity channelEntity = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));
        channelEntity.increaseCurPlayers();

        channelRepository.save(channelEntity);
        entity = userChannelRepository.save(entity);

        return new UserChannelGetResponseDto(entity);
    }

    @Override
    @Transactional
    public UserChannelGetResponseDto enterUserChannelByCode(String code) {
        User user = userService.loadUser();
        ChannelEntity channelEntity = channelRepository.findByCode(code)
                .orElseThrow(() -> new ChannelNotFoundException("No entity exist by code!"));
        UserChannelEntity entity = UserChannelEntity.builder()
                .userId(user.getId())
                .channelId(channelEntity.getId())
                .build();
        channelEntity.increaseCurPlayers();

        channelRepository.save(channelEntity);
        entity = userChannelRepository.save(entity);

        return new UserChannelGetResponseDto(entity);
    }

    @Override
    @Transactional
    public Long deleteExitUserChannel() {
        User user = userService.loadUser();
        UserChannelEntity entity = userChannelRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UserChannelNotFoundException("No entity exist by userId!"));
        Long response = entity.getId();

        ChannelEntity channel = channelRepository.findById(entity.getChannelId())
                .orElseThrow(() -> new ChannelNotFoundException(entity.getChannelId()));
        channel.decreaseCurPlayers();

        channelRepository.save(channel);
        userChannelRepository.delete(entity);

        return response;
    }

    @Override
    @Transactional
    public Long deleteExitUserChannelByChannelCodeAndUserId(Long userId, String channelCode) {
        ChannelEntity channelEntity = channelRepository.findByCode(channelCode)
                .orElseThrow(() -> new ChannelNotFoundException("No entity exist by channelCode!"));
        channelEntity.decreaseCurPlayers();

        UserChannelEntity entity = userChannelRepository.findByUserIdAndChannelId(userId, channelEntity.getId())
                .orElseThrow(() -> new UserChannelNotFoundException("No entity exist by userId, channelId!"));
        Long response = entity.getId();

        channelRepository.save(channelEntity);
        userChannelRepository.delete(entity);

        return response;
    }
}
