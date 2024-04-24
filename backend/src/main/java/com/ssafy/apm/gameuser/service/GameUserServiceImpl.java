package com.ssafy.apm.gameuser.service;

import com.ssafy.apm.gameuser.domain.GameUserEntity;
import com.ssafy.apm.gameuser.dto.response.GameUserDetailResponseDto;
import com.ssafy.apm.gameuser.repository.GameUserRepository;
import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.dto.UserDetailResponseDto;
import com.ssafy.apm.user.repository.UserRepository;
import com.ssafy.apm.userchannel.domain.UserChannelEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameUserServiceImpl implements GameUserService{

    private final GameUserRepository gameUserRepository;
    private final UserRepository userRepository;
    @Override
    public List<GameUserDetailResponseDto> getGameUserList(Long gameId) {
//        GameId로 게임방 안에 있는 게임유저 데이터들을 가져옴
        List<GameUserEntity> gameUserEntityList = gameUserRepository.findAllByGameId(gameId);
//        userId들 추출
        List<Long> userIds = gameUserEntityList.stream()
                .map(GameUserEntity::getUserId)
                .toList();
//        userId로 User 데이터들 추출
        List<User> userList = userRepository.findAllById(userIds);
        HashMap<Long, GameUserDetailResponseDto> map = new HashMap<>();
        List<GameUserDetailResponseDto> responseDtos = new ArrayList<>();

        for(GameUserEntity gameUser: gameUserEntityList){
//            map에 key값을 userId로 두고 value에 Dto를 넣음
            map.put(gameUser.getUserId(), new GameUserDetailResponseDto(gameUser));
        }
        for(User user: userList){
//            user Entity의 Id와 같은 값을 가진 dto들을 가져옴
            GameUserDetailResponseDto temp = map.get(user.getId());
//            dto에 user data set해줌
            temp.setUser(user);
//            리스트에 넣고 반환
            responseDtos.add(temp);
        }
        return responseDtos;
    }
}
