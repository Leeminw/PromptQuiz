package com.ssafy.apm.gamemonitor.service;

import com.ssafy.apm.socket.util.GameRoomStatus;
import com.ssafy.apm.gamemonitor.domain.GameMonitor;
import com.ssafy.apm.gamemonitor.repository.GameMonitorRepository;

import java.util.List;
import java.time.Instant;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameMonitorServiceImpl implements GameMonitorService {

    private final GameMonitorRepository gameMonitorRepository;

    @Override
    public void saveRoomList(ConcurrentHashMap<Long, GameRoomStatus> gameEndMap, ConcurrentHashMap<Long, GameRoomStatus> gameReadyMap, ConcurrentHashMap<Long, GameRoomStatus> gameOngoingMap) {
        try {
            List<GameMonitor> gameMonitorList = new ArrayList<>();

            Instant currentTime = Instant.now();
            gameEndMap.values().forEach(item -> gameMonitorList.add(GameMonitor.fromRoomStatus(item, currentTime)));
            gameReadyMap.values().forEach(item -> gameMonitorList.add(GameMonitor.fromRoomStatus(item, currentTime)));
            gameOngoingMap.values().forEach(item -> gameMonitorList.add(GameMonitor.fromRoomStatus(item, currentTime)));

            gameMonitorRepository.saves(gameMonitorList);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }
}
