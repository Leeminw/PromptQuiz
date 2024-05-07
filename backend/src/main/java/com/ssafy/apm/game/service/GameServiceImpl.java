package com.ssafy.apm.game.service;

import com.ssafy.apm.channel.domain.ChannelEntity;
import com.ssafy.apm.channel.exception.ChannelNotFoundException;
import com.ssafy.apm.channel.repository.ChannelRepository;
import com.ssafy.apm.game.domain.GameEntity;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import com.ssafy.apm.game.dto.response.GameAndChannelGetResponseDto;
import com.ssafy.apm.game.dto.response.GameGetResponseDto;
import com.ssafy.apm.game.exception.GameNotFoundException;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gamequiz.repository.GameQuizRepository;
import com.ssafy.apm.gameuser.domain.GameUserEntity;
import com.ssafy.apm.gameuser.exception.GameUserNotFoundException;
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

    private final UserService userService;
    private final GameRepository gameRepository;
    private final ChannelRepository channelRepository;
    private final GameUserRepository gameUserRepository;
    private final GameQuizRepository gameQuizRepository;

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
        List<GameEntity> entityList = gameRepository.findAllByChannelId(channelId)
                .orElseThrow(() -> new GameNotFoundException("No entities exists by channelId"));// 예외가 아니라 빈 리스트라도 던져야하나

        return entityList.stream()
                .map(GameGetResponseDto::new)
                .toList();

    }

    @Override
    public GameAndChannelGetResponseDto getGameInfo(Long gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));

        ChannelEntity channelEntity = channelRepository.findById(gameEntity.getChannelId())
                .orElseThrow(() -> new ChannelNotFoundException(gameEntity.getChannelId()));

        GameAndChannelGetResponseDto dto = new GameAndChannelGetResponseDto(gameEntity);
        dto.updateChannelInfo(channelEntity);


        return dto;
    }

    @Override
    @Transactional
    public GameGetResponseDto updateGameInfo(GameUpdateRequestDto gameUpdateRequestDto) {
        GameEntity gameEntity = gameRepository.findById(gameUpdateRequestDto.getId())
                .orElseThrow(() -> new GameNotFoundException(gameUpdateRequestDto.getId()));

        gameEntity.update(gameUpdateRequestDto);
        gameRepository.save(gameEntity);

        return new GameGetResponseDto(gameEntity);
    }

    @Override
    @Transactional
    public Integer updateGameRoundCnt(Long gameId, Boolean flag) {
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        Integer response = 0;
        if (flag) {
//            curRound 1로 초기화
            response = gameEntity.updateCurRound();
        } else {
//        마지막 라운드라면
            if (gameEntity.getCurRound() >= gameEntity.getRounds()) {
                return -1;
            }
            response = gameEntity.increaseRound();
        }
        gameRepository.save(gameEntity);
        return response;
    }

    @Override
    @Transactional
    public Long deleteGame(Long gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        List<GameUserEntity> list = gameUserRepository.findAllByGameId(gameId)
                .orElseThrow(() -> new GameUserNotFoundException("No entities exists by gameId"));

        gameUserRepository.deleteAll(list);
        gameRepository.delete(gameEntity);

        return gameId;
    }
}
