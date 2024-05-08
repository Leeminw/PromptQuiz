package com.ssafy.apm.gameuser.service;

import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.game.exception.GameNotFoundException;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gameuser.domain.GameUser;
import com.ssafy.apm.gameuser.dto.request.GameUserCreateRequestDto;
import com.ssafy.apm.gameuser.dto.request.GameUserUpdateRequestDto;
import com.ssafy.apm.gameuser.dto.response.GameUserDetailResponseDto;
import com.ssafy.apm.gameuser.dto.response.GameUserSimpleResponseDto;
import com.ssafy.apm.gameuser.exception.GameUserNotFoundException;
import com.ssafy.apm.gameuser.repository.GameUserRepository;
import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.dto.UserScoreUpdateRequestDto;
import com.ssafy.apm.user.exceptions.UserNotFoundException;
import com.ssafy.apm.user.repository.UserRepository;
import com.ssafy.apm.user.service.UserService;
import com.ssafy.apm.userchannel.domain.UserChannelEntity;
import com.ssafy.apm.userchannel.exception.UserChannelNotFoundException;
import com.ssafy.apm.userchannel.repository.UserChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameUserServiceImpl implements GameUserService {

    private final GameUserRepository gameUserRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public GameUserSimpleResponseDto createGameUser(GameUserCreateRequestDto requestDto) {
        GameUser gameUser = gameUserRepository.save(requestDto.toEntity());
        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    @Transactional
    public GameUserSimpleResponseDto updateGameUser(GameUserUpdateRequestDto requestDto) {
        GameUser gameUser = gameUserRepository.findById(requestDto.getCode()).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with code: " + requestDto.getCode()));
        gameUser = gameUserRepository.save(gameUser.update(requestDto));
        return new GameUserSimpleResponseDto(gameUser);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    @Transactional
    public GameUserSimpleResponseDto deleteGameUser(String code) {
        GameUser gameUser = gameUserRepository.findById(code).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with code: " + code));
        gameUserRepository.delete(gameUser);
        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    @Transactional
    public GameUserSimpleResponseDto deleteGameUser(String gameCode, Long userId) {
        GameUser gameUser = gameUserRepository.findByGameCodeAndUserId(gameCode, userId).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with " +
                        "GameCode, UserId: " + gameCode + ", " + userId));
        gameUserRepository.delete(gameUser);
        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    @Transactional
    public List<GameUserSimpleResponseDto> deleteGameUsersByGameCode(String gameCode) {
        List<GameUser> gameUsers = gameUserRepository.findAllByGameCode(gameCode).orElseThrow(
                () -> new GameUserNotFoundException("Entities Not Found with GameCode: " + gameCode));
        gameUserRepository.deleteAll(gameUsers);
        return gameUsers.stream().map(GameUserSimpleResponseDto::new).toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public GameUserSimpleResponseDto findGameUser(String code) {
        GameUser gameUser = gameUserRepository.findById(code).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with code: " + code));
        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    public GameUserSimpleResponseDto findGameUser(String gameCode, Long userId) {
        GameUser gameUser = gameUserRepository.findByGameCodeAndUserId(gameCode, userId).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with " +
                        "GameCode, UserId: " + gameCode + ", " + userId));
        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    public List<GameUserSimpleResponseDto> findSimpleGameUsersByGameCode(String gameCode) {
        List<GameUser> gameUsers = gameUserRepository.findAllByGameCode(gameCode).orElseThrow(
                () -> new GameUserNotFoundException("Entities Not Found with GameCode: " + gameCode));
        return gameUsers.stream().map(GameUserSimpleResponseDto::new).toList();
    }

    @Override
    public List<GameUserDetailResponseDto> findDetailGameUsersByGameCode(String gameCode) {
        List<GameUser> gameUsers = gameUserRepository.findAllByGameCode(gameCode).orElseThrow(
                () -> new GameUserNotFoundException("Entities Not Found with GameCode: " + gameCode));
        return gameUsers.stream().map(gameUser -> new GameUserDetailResponseDto(
                userRepository.findById(gameUser.getUserId()).orElseThrow(
                        () -> new UserNotFoundException(gameUser.getUserId())), gameUser
        )).toList();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    @Transactional
    public GameUserSimpleResponseDto updateGameUserTeam(String team) {
        Long userId = userService.loadUser().getId();
        GameUser gameUser = gameUserRepository.findByUserId(userId).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with UserId: " + userId));
        gameUser = gameUserRepository.save(gameUser.updateTeam(team));
        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    @Transactional
    public GameUserSimpleResponseDto updateGameUserScore(Integer score) {
        Long userId = userService.loadUser().getId();
        GameUser gameUser = gameUserRepository.findByUserId(userId).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with UserId: " + userId));
        gameUser = gameUserRepository.save(gameUser.updateScore(score));
        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    @Transactional
    public GameUserSimpleResponseDto updateGameUserIsHost(Boolean isHost) {
        Long userId = userService.loadUser().getId();
        GameUser gameUser = gameUserRepository.findByUserId(userId).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with UserId: " + userId));
        gameUser = gameUserRepository.save(gameUser.updateIsHost(isHost));
        return new GameUserSimpleResponseDto(gameUser);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* REFACTORED: findDetailGameUsersByGameCode() 으로 리팩토링됨 */
//    @Override
//    public List<GameUserDetailResponseDto> findDetailGameUsersByGameCode(String gameCode) {
////        GameId로 게임방 안에 있는 게임유저 데이터들을 가져옴
//        List<GameUser> gameUserList = gameUserRepository.findAllByGameCode(gameCode)
//                .orElseThrow(() -> new GameUserNotFoundException("No entities exists by gameId!"));
////        userId들 추출
//        List<Long> userIds = gameUserList.stream()
//                .map(GameUser::getUserId)
//                .toList();
////        userId로 User 데이터들 추출
//        List<User> userList = userRepository.findAllById(userIds);
//        HashMap<Long, GameUserDetailResponseDto> map = new HashMap<>();
//        List<GameUserDetailResponseDto> responseDtos = new ArrayList<>();
//
//        for (GameUser gameUser : gameUserList) {
////            map에 key값을 userId로 두고 value에 Dto를 넣음
//            map.put(gameUser.getUserId(), new GameUserDetailResponseDto(gameUser));
//        }
//        for (User user : userList) {
////            user Entity의 Id와 같은 값을 가진 dto들을 가져옴
//            GameUserDetailResponseDto temp = map.get(user.getId());
////            dto에 user data set해줌
//            temp.setUser(user);
////            리스트에 넣고 반환
//            responseDtos.add(temp);
//        }
//        return responseDtos;
//    }

    /* REFACTORED: findGameUser() 으로 리팩토링됨 */
//    @Override
//    public GameUserSimpleResponseDto getGameUser(Long gameUserId) {
//
//        GameUser entity = gameUserRepository.findById(gameUserId)
//                .orElseThrow(() -> new GameUserNotFoundException(gameUserId));
//
//        return new GameUserSimpleResponseDto(entity);
//    }

    /* FIXME: 입장 조건을 GameService 에서 판단하고,
        GameService 에서 createGameUser() 를 호출하는 것이 나아보임 */
//    //    게임 입장할때
//    @Override
//    @Transactional
//    public GameUserSimpleResponseDto postEnterGame(String gameCode) {
////        로그인 한놈 유저 정보 불러오기
//        User user = userService.loadUser();
//        Long userId = user.getId();
//
////        일반유저
//        GameUser entity = GameUser.builder()
//                .gameCode(gameCode)
//                .userId(userId)
//                .isHost(false)
//                .score(0)
//                .team("NOTHING")
//                .build();
//
//        Game game = gameRepository.findByCode(gameCode)
//                .orElseThrow(() -> new GameNotFoundException(gameCode));
//        if (game.getIsStarted()) { // 접속 불가능한 방이면
//            throw new RuntimeException("접속 불가능한 방입니다.");
//        }
////        방에 접속 중인 인원 하나 늘려줌
//        game.increaseCurPlayers();
//
//        gameRepository.save(game);
//        entity = gameUserRepository.save(entity);
//
//        return new GameUserSimpleResponseDto(entity);
//    }

    /* FIXME: GameService 로 이동하는 것이 나아보임 */
//    @Override
//    @Transactional
//    public GameUserSimpleResponseDto postFastEnterGame() {
////        Todo: 프론트에 던져줄 CustomException 만들기
////        로그인 한놈 유저 정보 불러오기
//        User user = userService.loadUser();
//        Long userId = user.getId();
//
//        UserChannelEntity userChannel = userChannelRepository.findByUserId(userId)
//                .orElseThrow(() -> new UserChannelNotFoundException("No entity exist by userId!"));
//
//        List<Game> gameList = gameRepository.findAllByChannelCode(userChannel.getChannelCode())
//                .orElseThrow(() -> new GameNotFoundException("No entities exists by channelId!"));// 채널에 생성된 방이 없다면
//
////        에러 코드를 프론트에서 받아 방을 만들 수 있게 처리해야함
//
//        for (Game entity : gameList) {
//            if (!entity.getIsStarted() && entity.getCurPlayers() < entity.getMaxPlayers()) { // 아직 입장할 수 있고 curPlayers가 maxPlayers보다 작을 때
//                //        일반유저
//                GameUser gameUser = GameUser.builder()
//                        .gameCode(entity.getCode())
//                        .userId(userId)
//                        .isHost(false)
//                        .score(0)
//                        .team("NOTHING")
//                        .build();
//
//                //        방에 접속 중인 인원 하나 늘려줌
//                entity.increaseCurPlayers();
//
//                gameRepository.save(entity);
//                gameUser = gameUserRepository.save(gameUser);
//
//                return new GameUserSimpleResponseDto(gameUser);
//            }
//        }
////        입장 가능한 방이 없으므로 방을 만들어야 한다고 프론트에 전달
//
//        return null;
//    }

//    @Override
//    @Transactional
//    public GameUserSimpleResponseDto updateGameUserIsReady(Boolean isReady) {
//        User user = userService.loadUser();
//        GameUser gameUser = gameUserRepository.findByUserId(user.getId())
//                .orElseThrow(() -> new GameUserNotFoundException("No entity exist by userId!"));
//        gameUser = gameUserRepository.save(gameUser);
//        return new GameUserSimpleResponseDto(gameUser);
//    }


    /* FIXME: GameService 로 이동하는 것이 나아보임 */
//    @Override
//    @Transactional
//    public void updateUserScore(String gameCode) {
//        Game game = gameRepository.findByCode(gameCode)
//                .orElseThrow(() -> new GameNotFoundException(gameCode));
//        List<GameUser> gameUserList = gameUserRepository.findAllByGameCode(gameCode)
//                .orElseThrow(() -> new GameUserNotFoundException("No entities exists by gameCode!"));
//        List<User> userList = new ArrayList<>();
//
////        Todo: Game-User의 Ranking을 여기서 구하고 점수를 넣는건지, 라운드마다 Ranking을 업데이트 하는지 상의하고 구현해야함.
//        int winnerListSize = gameUserList.size() / 2;
//        int loserListSize = winnerListSize + gameUserList.size() % 2;
//        int totalScore = 10 * game.getMaxRounds();
////        12가 최대 게임 참여자 수
//        int getWinnerMaxScore = Math.round(totalScore * (Math.min(game.getCurPlayers(), 12) / 12));
//
//        //            score를 기준으로 높은 순서대로 리스트가 정렬됨
//        List<GameUser> GameUsers = gameUserList.stream()
//                .map(obj -> (GameUser) obj)
//                .sorted((user1, user2) -> user2.getScore().compareTo(user1.getScore()))
//                .toList();
//
////        팀전일때
//        if (game.getIsTeam()) {
////            테스트 완료되면 리팩토링 해야합니다 ㅎㅎ..
//            int redTeamTotalScore = 0, blueTeamTotalScore = 0;
//            List<GameUser> redTeamEntity = new ArrayList<>();
//            List<GameUser> blueTeamEntity = new ArrayList<>();
//
////            누가 이겼는가
//            for (GameUser entity : GameUsers) {
//                if (entity.getTeam() == "RED") {
//                    redTeamTotalScore += entity.getScore();
//                    redTeamEntity.add(entity);
//                } else {
//                    blueTeamTotalScore += entity.getScore();
//                    blueTeamEntity.add(entity);
//                }
//            }
////            RedTeam이 점수가 더 높다면
//            if (redTeamTotalScore > blueTeamTotalScore) {
//                winnerTeamScore(userList, redTeamEntity, getWinnerMaxScore);
//                loserTeamScore(userList, blueTeamEntity, getWinnerMaxScore);
//            }
////            Blue팀의 점수가 더 높다면
//            else {
//                winnerTeamScore(userList, blueTeamEntity, getWinnerMaxScore);
//                loserTeamScore(userList, redTeamEntity, getWinnerMaxScore);
//            }
//        }
////        개인전일때
//        else {
////            점수 받는 놈들 로직
//            /* 10라운드 6명 기준
//             * getWinnerMaxScore = 10*10(10라운드) * (curPlayers/maxPlayers) = 50( 6명이서 게임할 때 1등이 받을 점수 )
//             * 1등 : 50 * 0.8^0 = 50,  2등 : 50 * 0.8^1 = 40, 3등 : 50*0.8^2 = 32
//             * 절반 이상부터는 점수 잃는 놈들
//             * 4등 : -50 * 0.8^2(3-1) = -32, 5등 : -50 * 0.8^1(3-2) = -40, 6등 : -40 * 0.8^0 = -50
//             *
//             * */
//            /* 10라운드 7명 기준
//             * getWinnerMaxScore = 10*10(10라운드) * (curPlayers/maxPlayers) = 58( 7명이서 게임할 때 1등이 받을 점수, 반올림 )
//             * 1등 : 58 * 0.8^0 = 58,  2등 : 58 * 0.8^1 = 46(반올림), 3등 : 58 * 0.8^2 = 37(반올림)
//             * 절반 이상부터는 점수 잃는 놈들
//             * 4등 : -58 * 0.8^3(4-1) = -29, 5등 : -58 * 0.8^2(4-2) = -37, 6등 : -58 * 0.8^1(4-3) : -46, 7등 : -58 * 0.8^0 : -58
//             *
//             * */
//            for (int i = 0; i < winnerListSize; i++) {
//                GameUser entity = GameUsers.get(i);
//                User user = userRepository.findById(entity.getUserId())
//                        .orElseThrow(() -> new UserNotFoundException(entity.getUserId()));
//
//                int earnUserScore = (int) Math.round(getWinnerMaxScore * Math.pow(0.8, i));
//
//                UserScoreUpdateRequestDto dto = UserScoreUpdateRequestDto.builder()
//                        .soloScore(earnUserScore)
//                        .totalScore(earnUserScore)
//                        .teamScore(0)
//                        .build();
//
//                user.updateScore(dto);
//                userList.add(user);
//            }
////            점수 잃는 놈들 로직
//            int j = 1;
//            for (int i = winnerListSize; i < GameUsers.size(); i++) {
//                GameUser entity = GameUsers.get(i);
//                User user = userRepository.findById(entity.getUserId())
//                        .orElseThrow(() -> new UserNotFoundException(entity.getUserId()));
//
//                int loseUserScore = -1 * (int) Math.round(getWinnerMaxScore * Math.pow(0.8, loserListSize - j++));
//
//                UserScoreUpdateRequestDto dto = UserScoreUpdateRequestDto.builder()
//                        .soloScore(loseUserScore)
//                        .totalScore(loseUserScore)
//                        .teamScore(0)
//                        .build();
//
//                user.updateScore(dto);
//                userList.add(user);
//            }
//        }
//        userRepository.saveAll(userList);
//    }

    /* FIXME 1: 퇴장 조건을 GameService 에서 판단하고,
        GameService 에서 deleteGameUser() 를 호출하는 것이 나아보임 */
    /* FIXME 2: 혹은 단순 deleteGameUser API 호출을 통해 처리하고,
        WebSocket 통신이 끊어지는 경우에도, deleteGameUser()를 호출하는 것이 나아보임*/
//    // 게임 나갈때
//    @Override
//    @Transactional
//    public Long deleteExitGame(Long gameId) {
//
//        User user = userService.loadUser();
//        Long userId = user.getId();
//        GameUser gameUser = gameUserRepository.findByGameIdAndUserId(gameId, userId)
//                .orElseThrow(() -> new GameUserNotFoundException("No entity exist by gameId, userId"));
//
//        Game game = gameRepository.findById(gameId)
//                .orElseThrow(() -> new GameNotFoundException(gameId));
//
//        Long gameUserId = gameUser.getId();
//
//        if (game.getCurPlayers() == 1) {// 방에 방장 혼자였다면
//            gameRepository.delete(game);// 방 자체를 지움
//        } else {
//            game.decreaseCurPlayers();// 방 현재 인원 수를 줄임
//            gameRepository.save(game);
//            if (gameUser.getIsHost()) {// 나가는 유저가 방장이라면
//                List<GameUser> userList = gameUserRepository.findAllByGameId(game.getId())
//                        .orElseThrow(() -> new GameUserNotFoundException("No entities exists by gameId"));// 방 안에 있는 유저 목록 가져와서
//                for (GameUser entity : userList) {
//                    if (!entity.getIsHost()) {// 방장이 아닌 놈을 찾아서 방장 권한을 준다
//                        entity.updateIsHost(true);
//                        gameUserRepository.save(entity);
//                        break;
//                    }
//                }
//            }
//        }
//        gameUserRepository.delete(gameUser);
//
//        return gameUserId;
//    }

    /* FIXME: 중복기능으로 판단됨, userId 보단 해시 ID인 code로 처리하는 것이 나아보임. */
//    @Override
//    @Transactional
//    public Long deleteExitGameByUserId(Long userId, String gameCode) {
//
//        Game game = gameRepository.findByCode(gameCode)
//                .orElseThrow(() -> new GameNotFoundException("No entity exist by code!"));
//
//        GameUser gameUser = gameUserRepository.findByGameIdAndUserId(game.getId(), userId)
//                .orElseThrow(() -> new GameUserNotFoundException("No entity exist by gameId, userId!"));
//        Long gameUserId = gameUser.getId();
//
//        if (game.getCurPlayers() == 1) {// 방에 방장 혼자였다면
//            gameRepository.delete(game);// 방 자체를 지움
//        } else {
//            game.decreaseCurPlayers();// 방 현재 인원 수를 줄임
//            gameRepository.save(game);
//            if (gameUser.getIsHost()) {// 나가는 유저가 방장이라면
//                List<GameUser> userList = gameUserRepository.findAllByGameId(game.getId())
//                        .orElseThrow(() -> new GameUserNotFoundException("No entities exists by gameId!"));// 방 안에 있는 유저 목록 가져와서
//                for (GameUser entity : userList) {
//                    if (!entity.getIsHost()) {// 방장이 아닌 놈을 찾아서 방장 권한을 준다
//                        entity.updateIsHost(true);
//                        gameUserRepository.save(entity);
//                        break;
//                    }
//                }
//            }
//        }
//
//        gameUserRepository.delete(gameUser);
//
//        return gameUserId;
//    }


    /* FIXME: GameService 로 이동하는 것이 나아보임 */
    // 이긴 팀 점수 계산
//    public void winnerTeamScore(List<User> userList, List<GameUser> winnerTeamEntity, int getWinnerMaxScore) {
//        for (int i = 0; i < winnerTeamEntity.size(); i++) {
//            GameUser entity = winnerTeamEntity.get(i);
//            User user = userRepository.findById(entity.getUserId())
//                    .orElseThrow(() -> new UserNotFoundException(entity.getUserId()));
//
//            int earnUserScore = (int) Math.round(getWinnerMaxScore * Math.pow(0.8, i));
//
//            UserScoreUpdateRequestDto dto = UserScoreUpdateRequestDto.builder()
//                    .soloScore(0)
//                    .totalScore(earnUserScore)
//                    .teamScore(earnUserScore)
//                    .build();
//
//            user.updateScore(dto);
//            userList.add(user);
//        }
//    }

    /* FIXME: GameService 로 이동하는 것이 나아보임 */
//    public void loserTeamScore(List<User> userList, List<GameUser> loserTeamEntity, int getWinnerMaxScore) {
//        //            점수 잃는 놈들 로직
//        int j = 1;
//        for (int i = 0; i < loserTeamEntity.size(); i++) {
//            GameUser entity = loserTeamEntity.get(i);
//            User user = userRepository.findById(entity.getUserId())
//                    .orElseThrow(() -> new UserNotFoundException(entity.getUserId()));
//
//            int loseUserScore = -1 * (int) Math.round(getWinnerMaxScore * Math.pow(0.8, loserTeamEntity.size() - j++));
//
//            UserScoreUpdateRequestDto dto = UserScoreUpdateRequestDto.builder()
//                    .soloScore(loseUserScore)
//                    .totalScore(loseUserScore)
//                    .teamScore(0)
//                    .build();
//
//            user.updateScore(dto);
//            userList.add(user);
//        }
//    }

}
