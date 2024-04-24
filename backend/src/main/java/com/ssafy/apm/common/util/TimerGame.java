package com.ssafy.apm.common.util;

public class TimerGame {
    public Long uuid;
    public Long quizId;
    public Integer time;
    public Integer maxTime;

    public TimerGame(Long uuid, Long quizId, Integer maxTime, Integer time) {
        this.uuid = uuid;
        this.quizId = quizId;
        this.maxTime = maxTime;
        this.time = time;
    }

}
