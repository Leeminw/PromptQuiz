package com.ssafy.apm.gamemonitor.domain;

import com.influxdb.query.FluxRecord;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.ssafy.apm.common.util.GameRoomStatus;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.time.ZoneId;
import java.time.Instant;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@Measurement(name = "game_monitor")
public class GameMonitor {

    @Column(timestamp = true)
    private Instant time;
    @Column(tag = true)
    private String uuid;

    public GameMonitor(FluxRecord fluxRecord) {
        this.time = fluxRecord.getTime();
        this.uuid = (String) fluxRecord.getValueByKey("uuid");
    }

    public String getLocalTime() {
        return LocalTime.ofInstant(this.time, ZoneId.of("UTC")).toString();
    }

    public static GameMonitor fromRoomStatus(GameRoomStatus game, Instant time) {
        return GameMonitor.builder().time(time).uuid(game.gameCode).build();
    }

}
