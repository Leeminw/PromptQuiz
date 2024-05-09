package com.ssafy.apm.userchannel.service;

import com.ssafy.apm.channel.domain.Channel;
import com.ssafy.apm.channel.exception.ChannelNotFoundException;
import com.ssafy.apm.channel.repository.ChannelRepository;
import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.dto.UserDetailResponseDto;
import com.ssafy.apm.user.repository.UserRepository;
import com.ssafy.apm.user.service.UserService;
import com.ssafy.apm.userchannel.domain.UserChannel;
import com.ssafy.apm.userchannel.dto.response.UserChannelGetResponseDto;
import com.ssafy.apm.userchannel.exception.UserChannelNotFoundException;
import com.ssafy.apm.userchannel.repository.UserChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        List<UserChannel> userChannelEntityList = userChannelRepository.findAllByChannelId(channelId);
        List<Long> userIds = userChannelEntityList.stream()
                .map(UserChannel::getUserId)
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
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));

        deleteAllDummyUserChannel(user);

        UserChannel entity = UserChannel.builder()
                .userId(user.getId())
                .channelId(channelId)
                .build();
        channel.increaseCurPlayers();

        channelRepository.save(channel);
        entity = userChannelRepository.save(entity);

        return new UserChannelGetResponseDto(entity);
    }
    @Override
    @Transactional
    public UserChannelGetResponseDto enterUserChannelByCode(String code) {
        User user = userService.loadUser();
        Channel channel = channelRepository.findByCode(code)
                .orElseThrow(() -> new ChannelNotFoundException("No entity exist by code!"));

        deleteAllDummyUserChannel(user);

        UserChannel entity = UserChannel.builder()
                .userId(user.getId())
                .channelId(channel.getId())
                .build();
        channel.increaseCurPlayers();

        channelRepository.save(channel);
        entity = userChannelRepository.save(entity);

        return new UserChannelGetResponseDto(entity);
    }
    @Override
    @Transactional
    public Long deleteExitUserChannel() {
        User user = userService.loadUser();
        UserChannel entity = userChannelRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UserChannelNotFoundException("No entity exist by userId!"));
        Long response = entity.getId();

        Channel channel = channelRepository.findById(entity.getChannelId())
                .orElseThrow(() -> new ChannelNotFoundException(entity.getChannelId()));
        channel.decreaseCurPlayers();

        channelRepository.save(channel);
        userChannelRepository.delete(entity);

        return response;
    }
    @Override
    @Transactional
    public Long deleteExitUserChannelByUserIdAndCode(Long userId, String code) {
        Channel channel = channelRepository.findByCode(code)
                .orElseThrow(() -> new ChannelNotFoundException("No entity exist by code!"));
        channel.decreaseCurPlayers();

        UserChannel entity = userChannelRepository.findByUserIdAndChannelId(userId, channel.getId())
                .orElseThrow(() -> new UserChannelNotFoundException("No entity exist by userId, channelId!"));
        Long response = entity.getId();

        channelRepository.save(channel);
        userChannelRepository.delete(entity);

        return response;
    }
    private void deleteAllDummyUserChannel(User user) {
        if(userChannelRepository.existsByUserId(user.getId())){ // 이미 어디 채널에 들어가 있는 유저면
            List<UserChannel> dummyUserChannelList = userChannelRepository.findAllByUserId(user.getId())
                    .orElseThrow(() -> new UserChannelNotFoundException("No entities exists by userId!"));
            List<Channel> dummyChannelList = new ArrayList<>();
            for(UserChannel userChannel: dummyUserChannelList) {
                Channel temp = channelRepository.findById(userChannel.getChannelId()) // 그 놈들이 들어있는 채널 찾아서
                        .orElseThrow(() -> new ChannelNotFoundException(userChannel.getChannelId()));
                temp.decreaseCurPlayers(); // 그 놈이 들어있는 채널의 curPlayers를 줄여준 후
                dummyChannelList.add(temp);
            }
            channelRepository.saveAll(dummyChannelList); // 채널 curPlayers 변경 사항들 저장
            userChannelRepository.deleteAll(dummyUserChannelList); // 그 놈들 삭제
        }
    }
}
