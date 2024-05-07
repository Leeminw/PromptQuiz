package com.ssafy.apm.gamemonitor.service;

import com.ssafy.apm.common.util.GameRoomStatus;

import java.util.concurrent.ConcurrentHashMap;

public interface GameMonitorService {
    void saveRoomList(ConcurrentHashMap<Long, GameRoomStatus> gameEndMap, ConcurrentHashMap<Long, GameRoomStatus> gameReadyMap, ConcurrentHashMap<Long, GameRoomStatus> gameOngoingMap);
}
