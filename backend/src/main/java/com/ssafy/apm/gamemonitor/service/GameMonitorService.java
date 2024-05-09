package com.ssafy.apm.gamemonitor.service;

import com.ssafy.apm.common.util.GameRoomStatus;

import java.util.HashMap;

public interface GameMonitorService {

    void saveRoomList(HashMap<String, GameRoomStatus> gameEndMap,
        HashMap<String, GameRoomStatus> gameReadyMap,
        HashMap<String, GameRoomStatus> gameOngoingMap);

}
