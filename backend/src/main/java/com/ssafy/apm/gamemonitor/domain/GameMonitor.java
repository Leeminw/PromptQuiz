package com.ssafy.apm.gamemonitor.domain;

import com.influxdb.query.FluxRecord;
import com.ssafy.apm.game.domain.Game;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import lombok.Data;
import lombok.Builder;
import lombok.ToString;
import lombok.AllArgsConstructor;

import java.time.ZoneId;
import java.time.Instant;
import java.time.LocalTime;

@Data
@Builder
@ToString
@AllArgsConstructor
@Measurement(name = "game_monitor")
public class GameMonitor {

    @Column(timestamp = true)
    private Instant time;
    @Column(tag = true)
    private String state;
    @Column(name = "uuid")
    private String uuid;

    public GameMonitor(FluxRecord fluxRecord) {
        this.time = fluxRecord.getTime();
        this.uuid = (String) fluxRecord.getValueByKey("uuid");
        this.state = (String) fluxRecord.getValueByKey("state");
    }

    public String getLocalTime() {
        return LocalTime.ofInstant(this.time, ZoneId.of("UTC")).toString();
    }

    public static GameMonitor fromGame(Game game, Instant time) {
        return GameMonitor.builder().time(time).uuid(game.getCode()).state(game.getIsStarted() ? "진행중" : "대기중").build();
    }

}
