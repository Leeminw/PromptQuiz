package com.ssafy.apm.gamemonitor.service;

import com.ssafy.apm.socket.util.GameRoomStatus;

import java.util.List;

public interface GameMonitorService {
    void save(List<GameRoomStatus> list);
}
