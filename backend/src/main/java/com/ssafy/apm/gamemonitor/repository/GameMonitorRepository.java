package com.ssafy.apm.gamemonitor.repository;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import com.ssafy.apm.common.config.InfluxDBConfig;
import com.ssafy.apm.gamemonitor.domain.GameMonitor;
import com.ssafy.apm.gamemonitor.exception.GameMonitorValidationException;

import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;

@Repository
@RequiredArgsConstructor
public class GameMonitorRepository {
    private final InfluxDBConfig influxDBConfig;

    // 기본 DB 정보 로드
    @Value("${spring.influx.org}")
    private String org;

    @Value("${spring.influx.bucket}")
    private String bucket;

    public void saves(List<GameMonitor> gameMonitorList) {
        try (InfluxDBClient influxDBClient = influxDBConfig.getConnectionInfluxDBClient()) {
            influxDBClient.getWriteApiBlocking().writeMeasurements(bucket, org, WritePrecision.NS, gameMonitorList);
        } catch (Exception e) {
            throw new GameMonitorValidationException(e.getMessage());
        }
    }
}
