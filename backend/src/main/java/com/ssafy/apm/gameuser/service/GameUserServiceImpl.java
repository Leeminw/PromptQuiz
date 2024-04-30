package com.ssafy.apm.gameuser.service;

import com.ssafy.apm.game.domain.GameEntity;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gameuser.domain.GameUserEntity;
import com.ssafy.apm.gameuser.dto.response.GameUserDetailResponseDto;
import com.ssafy.apm.gameuser.dto.response.GameUserGetResponseDto;
import com.ssafy.apm.gameuser.repository.GameUserRepository;
import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.dto.UserDetailResponseDto;
import com.ssafy.apm.user.dto.UserScoreUpdateRequestDto;
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

    private final GameRepository gameRepository;
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

    @Override
    @Transactional
    public void updateUserScore(Long gameId) {
        GameEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new NoSuchElementException("그런 게임방이 존재하지 않습니다"));
        List<GameUserEntity> gameUserEntityList = gameUserRepository.findAllByGameId(gameId);
        List<User> userList = new ArrayList<>();

//        Todo: Game-User의 Ranking을 여기서 구하고 점수를 넣는건지, 라운드마다 Ranking을 업데이트 하는지 상의하고 구현해야함.
        int winnerListSize = gameUserEntityList.size() / 2;
        int loserListSize = winnerListSize + gameUserEntityList.size() % 2;
        int totalScore = 10 * game.getRounds();
//        12가 최대 게임 참여자 수
        int getWinnerMaxScore = Math.round(totalScore * (Math.min(game.getCurPlayers(), 12) / 12));

        //            score를 기준으로 높은 순서대로 리스트가 정렬됨
        List<GameUserEntity> GameUsers = gameUserEntityList.stream()
                .map(obj -> (GameUserEntity) obj)
                .sorted((user1, user2) -> user2.getScore().compareTo(user1.getScore()))
                .toList();

//        팀전일때
        if (game.getIsTeam()) {
//            Todo:팀전일 때 점수 구하는 로직 작성해야 합니다.
//            테스트 완료되면 리팩토링 해야합니다 ㅎㅎ..
            int redTeamTotalScore = 0, blueTeamTotalScore = 0;
            List<GameUserEntity> redTeamEntity = new ArrayList<>();
            List<GameUserEntity> blueTeamEntity = new ArrayList<>();

//            누가 이겼는가
            for (GameUserEntity entity : GameUsers) {
                if (entity.getTeam() == "RED") {
                    redTeamTotalScore += entity.getScore();
                    redTeamEntity.add(entity);
                } else {
                    blueTeamTotalScore += entity.getScore();
                    blueTeamEntity.add(entity);
                }
            }
//            RedTeam이 점수가 더 높다면
            if (redTeamTotalScore > blueTeamTotalScore) {
                winnerTeamScore(userList, redTeamEntity, getWinnerMaxScore);
                loserTeamScore(userList, blueTeamEntity, getWinnerMaxScore);
            }
//            Blue팀의 점수가 더 높다면
            else {
                winnerTeamScore(userList, blueTeamEntity, getWinnerMaxScore);
                loserTeamScore(userList, redTeamEntity, getWinnerMaxScore);
            }
        }
//        개인전일때
        else {
//            Todo:개인전일 때 점수 구하는 로직 작성해야 합니다.
//            점수 받는 놈들 로직
            /* 10라운드 6명 기준
             * getWinnerMaxScore = 10*10(10라운드) * (curPlayers/maxPlayers) = 50( 6명이서 게임할 때 1등이 받을 점수 )
             * 1등 : 50 * 0.8^0 = 50,  2등 : 50 * 0.8^1 = 40, 3등 : 50*0.8^2 = 32
             * 절반 이상부터는 점수 잃는 놈들
             * 4등 : -50 * 0.8^2(3-1) = -32, 5등 : -50 * 0.8^1(3-2) = -40, 6등 : -40 * 0.8^0 = -50
             *
             * */
            /* 10라운드 7명 기준
             * getWinnerMaxScore = 10*10(10라운드) * (curPlayers/maxPlayers) = 58( 7명이서 게임할 때 1등이 받을 점수, 반올림 )
             * 1등 : 58 * 0.8^0 = 58,  2등 : 58 * 0.8^1 = 46(반올림), 3등 : 58 * 0.8^2 = 37(반올림)
             * 절반 이상부터는 점수 잃는 놈들
             * 4등 : -58 * 0.8^3(4-1) = -29, 5등 : -58 * 0.8^2(4-2) = -37, 6등 : -58 * 0.8^1(4-3) : -46, 7등 : -58 * 0.8^0 : -58
             *
             * */
            for (int i = 0; i < winnerListSize; i++) {
                GameUserEntity entity = GameUsers.get(i);
                User user = userRepository.findById(entity.getUserId())
                        .orElseThrow(() -> new NoSuchElementException("그런 유저는 없다."));

                int earnUserScore = (int) Math.round(getWinnerMaxScore * Math.pow(0.8, i));

                UserScoreUpdateRequestDto dto = UserScoreUpdateRequestDto.builder()
                        .soloScore(earnUserScore)
                        .totalScore(earnUserScore)
                        .teamScore(0)
                        .build();

                user.updateScore(dto);
                userList.add(user);
            }
//            점수 잃는 놈들 로직
            int j = 1;
            for (int i = winnerListSize; i < GameUsers.size(); i++) {
                GameUserEntity entity = GameUsers.get(i);
                User user = userRepository.findById(entity.getUserId())
                        .orElseThrow(() -> new NoSuchElementException("그런 유저는 없다."));

                int loseUserScore = -1 * (int) Math.round(getWinnerMaxScore * Math.pow(0.8, loserListSize - j++));

                UserScoreUpdateRequestDto dto = UserScoreUpdateRequestDto.builder()
                        .soloScore(loseUserScore)
                        .totalScore(loseUserScore)
                        .teamScore(0)
                        .build();

                user.updateScore(dto);
                userList.add(user);
            }
        }
        userRepository.saveAll(userList);
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

    @Override
    @Transactional
    public Long deleteExitGameByUserId(Long userId, String gameCode) {
        GameEntity gameEntity = gameRepository.findByCode(gameCode)
                .orElseThrow(() -> new NoSuchElementException("해당 code를 가진 게임을 찾을 수 없습니다."));

        GameUserEntity gameUserEntity = gameUserRepository.findByGameIdAndUserId(gameEntity.getId(), userId)
                .orElseThrow(() -> new NoSuchElementException("게임 유저 테이블을 찾지 못했습니다"));

        Long gameUserId = gameUserEntity.getId();

        gameUserRepository.delete(gameUserEntity);

        return gameUserId;
    }


    //    이긴 팀 점수 계산
    public void winnerTeamScore(List<User> userList, List<GameUserEntity> winnerTeamEntity, int getWinnerMaxScore) {
        for (int i = 0; i < winnerTeamEntity.size(); i++) {
            GameUserEntity entity = winnerTeamEntity.get(i);
            User user = userRepository.findById(entity.getUserId())
                    .orElseThrow(() -> new NoSuchElementException("해당 ID를 가진 유저를 찾을 수 없습니다."));

            int earnUserScore = (int) Math.round(getWinnerMaxScore * Math.pow(0.8, i));

            UserScoreUpdateRequestDto dto = UserScoreUpdateRequestDto.builder()
                    .soloScore(0)
                    .totalScore(earnUserScore)
                    .teamScore(earnUserScore)
                    .build();

            user.updateScore(dto);
            userList.add(user);
        }
    }

    public void loserTeamScore(List<User> userList, List<GameUserEntity> loserTeamEntity, int getWinnerMaxScore) {
        //            점수 잃는 놈들 로직
        int j = 1;
        for (int i = 0; i < loserTeamEntity.size(); i++) {
            GameUserEntity entity = loserTeamEntity.get(i);
            User user = userRepository.findById(entity.getUserId())
                    .orElseThrow(() -> new NoSuchElementException("그런 유저는 없다."));

            int loseUserScore = -1 * (int) Math.round(getWinnerMaxScore * Math.pow(0.8, loserTeamEntity.size() - j++));

            UserScoreUpdateRequestDto dto = UserScoreUpdateRequestDto.builder()
                    .soloScore(loseUserScore)
                    .totalScore(loseUserScore)
                    .teamScore(0)
                    .build();

            user.updateScore(dto);
            userList.add(user);
        }
    }
}
