package com.ssafy.apm.common.util;

public class TimerGame {
    public Long gameId;
    public String uuid;
    public Integer round;
    public Integer time;
    public Integer maxTime;
    public TimerGame(Long gameId, String uuid, Integer round, Integer maxTime, Integer time) {
        this.gameId = gameId;
        this.uuid = uuid;
        this.round = round;
        this.maxTime = maxTime;
        this.time = time;
    }
}
