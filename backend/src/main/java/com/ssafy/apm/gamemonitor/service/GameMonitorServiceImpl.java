package com.ssafy.apm.gamemonitor.service;

import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gamemonitor.domain.GameMonitor;
import com.ssafy.apm.gamemonitor.repository.GameMonitorRepository;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.time.Instant;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameMonitorServiceImpl implements GameMonitorService {

    private final GameRepository gameRepository;
    private final GameMonitorRepository gameMonitorRepository;

    @Override
    public void saveRoomList() {
        try {
            List<GameMonitor> gameMonitorList = new ArrayList<>();
            Instant currentTime = Instant.now();
            List<Game> gameList = (List<Game>) gameRepository.findAll();
            for (Game game : gameList) {
                gameMonitorList.add(GameMonitor.fromGame(game, currentTime));
            }
            gameMonitorRepository.saves(gameMonitorList);

        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

}
