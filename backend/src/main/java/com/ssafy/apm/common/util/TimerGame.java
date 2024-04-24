package com.ssafy.apm.common.util;

public class TimerGame {
    public Long gameId;
    public String uuid;
    public Long quizId;
    public Integer time;
    public Integer maxTime;

    public TimerGame(Long gameId, String uuid, Long quizId, Integer maxTime, Integer time) {
        this.gameId = gameId;
        this.uuid = uuid;
        this.quizId = quizId;
        this.maxTime = maxTime;
        this.time = time;
    }
}
