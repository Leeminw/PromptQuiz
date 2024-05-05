package com.ssafy.apm.gamemonitor.domain;

import com.influxdb.annotations.Column;
import com.influxdb.query.FluxRecord;
import com.ssafy.apm.socket.util.GameRoomStatus;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import com.influxdb.annotations.Measurement;

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

    public static GameMonitor fromRoomStatus(GameRoomStatus game, Instant time){
        return GameMonitor.builder().time(time).uuid(game.uuid).build();
    }
}
