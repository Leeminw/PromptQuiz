package com.ssafy.apm.game;

import com.ssafy.apm.game.domain.GameEntity;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.game.service.GameServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameTest {

    @Autowired
    private GameServiceImpl gameService;

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void createGameTest(){
//        객관식, 실사체
        GameCreateRequestDto dto = new GameCreateRequestDto(1L,1L,0,0,"들어와",null,true,false,0,0,1,8);
        GameEntity gameEntity = dto.toEntity();
        gameRepository.save(gameEntity);
        System.out.println("찾았다!!!" + gameRepository.findById(gameEntity.getId()));
//                .orElseThrow(null));

        gameRepository.delete(gameEntity);
    }
}
