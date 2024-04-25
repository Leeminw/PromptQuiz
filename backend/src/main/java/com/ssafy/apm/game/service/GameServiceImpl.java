package com.ssafy.apm.game.service;

import com.ssafy.apm.game.domain.GameEntity;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import com.ssafy.apm.game.dto.response.GameGetResponseDto;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gamequiz.repository.GameQuizRepository;
import com.ssafy.apm.gameuser.domain.GameUserEntity;
import com.ssafy.apm.gameuser.repository.GameUserRepository;
import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameUserRepository gameUserRepository;
    private final GameQuizRepository gameQuizRepository;
    private final UserService userService;

    @Override
    @Transactional
    public GameGetResponseDto createGame(GameCreateRequestDto gameCreateRequestDto) {
        User userEntity = userService.loadUser();
        GameEntity gameEntity = gameCreateRequestDto.toEntity();
        gameEntity = gameRepository.save(gameEntity);
        /*
         * 방 만든 사람의 GameUser Entity도 생성해서 저장
         * */
        GameUserEntity gameUserEntity = GameUserEntity.builder()
                .gameId(gameEntity.getId())
                .userId(userEntity.getId())
                .isHost(true)
                .isReady(true)
                .score(0)
                .team("NOTHING")
                .ranking(0)
                .build();
        gameUserRepository.save(gameUserEntity);

        return new GameGetResponseDto(gameEntity);
    }

    @Override
    public List<GameGetResponseDto> getGameList(Long channelId) {
        List<GameEntity> entityList = gameRepository.findAllByChannelId(channelId);

        return entityList.stream()
                .map(GameGetResponseDto::new)
                .toList();

    }

    @Override
    public GameGetResponseDto getGameInfo(Long gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게임방입니다."));

        return new GameGetResponseDto(gameEntity);
    }

    @Override
    @Transactional
    public GameGetResponseDto updateGameInfo(GameUpdateRequestDto gameUpdateRequestDto) {
        GameEntity gameEntity = gameRepository.findById(gameUpdateRequestDto.getId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게임방입니다."));

        gameEntity.update(gameUpdateRequestDto);
        gameRepository.save(gameEntity);

        return new GameGetResponseDto(gameEntity);
    }

    @Override
    @Transactional
    public Integer updateGameRoundCnt(Long gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게임방입니다."));

//        마지막 라운드라면
        if (gameEntity.getCurRound() >= gameEntity.getRounds()) {
            return -1;
        }
        Integer response = gameEntity.increaseRound();
        gameRepository.save(gameEntity);
        return response;
    }

    @Override
    @Transactional
    public Long deleteGame(Long gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게임방입니다."));
        List<GameUserEntity> list = gameUserRepository.findAllByGameId(gameId);

        gameUserRepository.deleteAll(list);
        gameRepository.delete(gameEntity);

        return gameId;
    }
}
