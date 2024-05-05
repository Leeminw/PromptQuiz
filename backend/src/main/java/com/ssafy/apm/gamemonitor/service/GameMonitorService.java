package com.ssafy.apm.gamemonitor.service;

import com.ssafy.apm.socket.util.GameRoomStatus;

import java.util.HashMap;

public interface GameMonitorService {
    void saveRoomList(HashMap<Long, GameRoomStatus> gameEndMap, HashMap<Long, GameRoomStatus> gameReadyMap, HashMap<Long, GameRoomStatus> gameOngoingMap);
}
