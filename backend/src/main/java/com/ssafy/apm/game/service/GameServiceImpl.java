package com.ssafy.apm.game.service;

import com.ssafy.apm.game.domain.GameEntity;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.response.GameGetResponseDto;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gameuser.domain.GameUserEntity;
import com.ssafy.apm.gameuser.repository.GameUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameUserRepository gameUserRepository;
    @Override
    @Transactional
    public void createGame(GameCreateRequestDto gameCreateRequestDto) {
        GameEntity gameEntity = gameCreateRequestDto.toEntity();
        gameEntity = gameRepository.save(gameEntity);
        GameUserEntity gameUserEntity = GameUserEntity.builder()
                .gameId(gameEntity.getId())
//                .gameId(1L)
                .userId(gameCreateRequestDto.getUserId())
                .isHost(true)
                .isReady(true)
                .score(0)
                .team("RED")
                .ranking(0)
                .build();
        gameUserRepository.save(gameUserEntity);
    }

    @Override
    public List<GameGetResponseDto> getGameList(Long channelId) {
        List<GameEntity> entityList = gameRepository.findAllByChannelId(channelId);

        return entityList.stream()
                .map(GameGetResponseDto::new)
                .toList();

    }
}
