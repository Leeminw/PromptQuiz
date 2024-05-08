package com.ssafy.apm.gamemonitor.service;

import com.ssafy.apm.common.util.GameRoomStatus;

import java.util.concurrent.ConcurrentHashMap;

public interface GameMonitorService {

    void saveRoomList(ConcurrentHashMap<String, GameRoomStatus> gameEndMap,
                      ConcurrentHashMap<String, GameRoomStatus> gameReadyMap,
                      ConcurrentHashMap<String, GameRoomStatus> gameOngoingMap);

}
