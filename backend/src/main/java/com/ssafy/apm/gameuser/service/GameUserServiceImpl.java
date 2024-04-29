package com.ssafy.apm.gameuser.service;

import com.ssafy.apm.gameuser.domain.GameUserEntity;
import com.ssafy.apm.gameuser.dto.response.GameUserDetailResponseDto;
import com.ssafy.apm.gameuser.dto.response.GameUserGetResponseDto;
import com.ssafy.apm.gameuser.repository.GameUserRepository;
import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.dto.UserDetailResponseDto;
import com.ssafy.apm.user.repository.UserRepository;
import com.ssafy.apm.user.service.UserService;
import com.ssafy.apm.userchannel.domain.UserChannelEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameUserServiceImpl implements GameUserService {

    private final GameUserRepository gameUserRepository;
    private final UserRepository userRepository;
    private final UserService userService;

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

        for (GameUserEntity gameUser : gameUserEntityList) {
//            map에 key값을 userId로 두고 value에 Dto를 넣음
            map.put(gameUser.getUserId(), new GameUserDetailResponseDto(gameUser));
        }
        for (User user : userList) {
//            user Entity의 Id와 같은 값을 가진 dto들을 가져옴
            GameUserDetailResponseDto temp = map.get(user.getId());
//            dto에 user data set해줌
            temp.setUser(user);
//            리스트에 넣고 반환
            responseDtos.add(temp);
        }
        return responseDtos;
    }

    //    게임 입장할때
    @Override
    @Transactional
    public GameUserGetResponseDto postEnterGame(Long gameId) {
//        로그인 한놈 유저 정보 불러오기
        User user = userService.loadUser();
        Long userId = user.getId();

//        일반유저
        GameUserEntity entity = GameUserEntity.builder()
                .gameId(gameId)
                .userId(userId)
                .isHost(false)
                .isReady(false)
                .score(0)
                .team("NOTHING")
                .build();

        entity = gameUserRepository.save(entity);

        return new GameUserGetResponseDto(entity);
    }

    @Override
    @Transactional
    public GameUserGetResponseDto updateGameUserScore(Integer score) {
        User user = userService.loadUser();
        GameUserEntity gameUserEntity = gameUserRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException("게임 유저 테이블을 찾지 못했습니다"));

//        점수 업데이트
        gameUserEntity.updateScore(score);

//        점수 DB에 반영
        gameUserEntity = gameUserRepository.save(gameUserEntity);
        return new GameUserGetResponseDto(gameUserEntity);
    }

    @Override
    @Transactional
    public GameUserGetResponseDto updateGameUserIsReady(Boolean isReady) {
        User user = userService.loadUser();
        GameUserEntity gameUserEntity = gameUserRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException("게임 유저 테이블을 찾지 못했습니다"));

//        레디 상태 업데이트
        gameUserEntity.updateIsReady(isReady);

//        DB에 반영
        gameUserEntity = gameUserRepository.save(gameUserEntity);
        return new GameUserGetResponseDto(gameUserEntity);
    }

    @Override
    @Transactional
    public GameUserGetResponseDto updateGameUserTeam(String team) {
        User user = userService.loadUser();
        GameUserEntity gameUserEntity = gameUserRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException("게임 유저 테이블을 찾지 못했습니다"));

//        팀 업데이트
        gameUserEntity.updateTeam(team);

//        DB에 반영
        gameUserEntity = gameUserRepository.save(gameUserEntity);
        return new GameUserGetResponseDto(gameUserEntity);
    }

    //    게임 나갈때
    @Override
    @Transactional
    public Long deleteExitGame(Long gameId) {
        User user = userService.loadUser();
        Long userId = user.getId();
        GameUserEntity gameUserEntity = gameUserRepository.findByGameIdAndUserId(gameId, userId)
                .orElseThrow(() -> new NoSuchElementException("게임 유저 테이블을 찾지 못했습니다"));

        Long gameUserId = gameUserEntity.getId();

        gameUserRepository.delete(gameUserEntity);

        return gameUserId;
    }
}
