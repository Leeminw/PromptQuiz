package com.ssafy.apm.gamequiz.service;

import com.ssafy.apm.game.domain.GameEntity;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gamequiz.domain.GameQuizEntity;
import com.ssafy.apm.gamequiz.dto.response.GameQuizGetResponseDto;
import com.ssafy.apm.gamequiz.repository.GameQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameQuizServiceImpl implements GameQuizService{

    private final GameQuizRepository gameQuizRepository;
    private final GameRepository gameRepository;

//    맨 앞에 있는 놈을 뽑아서 보내줌
    @Override
    public GameQuizGetResponseDto getGameQuizDetail(Long gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게임방입니다."));

        Integer round = gameEntity.getRounds();
        GameQuizEntity dto = gameQuizRepository.findByGameIdAndRound(gameId,round)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 퀴즈입니다."));

        GameQuizGetResponseDto response = new GameQuizGetResponseDto(dto);

        return response;
    }
}
