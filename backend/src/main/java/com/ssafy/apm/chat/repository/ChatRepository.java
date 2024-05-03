package com.ssafy.apm.chat.repository;

import com.influxdb.query.FluxTable;
import com.influxdb.query.FluxRecord;
import com.ssafy.apm.chat.domain.Chat;
import com.influxdb.client.InfluxDBClient;
import com.ssafy.apm.chat.config.InfluxDBConfig;
import com.influxdb.client.domain.WritePrecision;
import com.ssafy.apm.chat.exception.ChatNotFoundException;
import com.ssafy.apm.chat.exception.ChatValidationException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.ArrayList;

@Repository
@RequiredArgsConstructor
public class ChatRepository {

    private final InfluxDBConfig influxDBConfig;

    // 기본 DB 정보 로드
    @Value("${spring.influx.org}")
    private String org;

    @Value("${spring.influx.bucket}")
    private String bucket;

    public Chat save(Chat chat) {
        try (InfluxDBClient influxDBClient = influxDBConfig.getConnectionInfluxDBClient()) {
            influxDBClient.getWriteApiBlocking().writeMeasurement(bucket, org, WritePrecision.NS, chat);
            return chat;
        } catch (Exception e) {
            throw new ChatValidationException(e.getMessage());
        }
    }

    public List<Chat> findByHourDuration(Integer hour) {
        if (hour == null) hour = 1;

        String flux = "from(bucket: \"" + bucket + "\")\n"
                + "  |> range(start: " + hour + "h)\n"
                + "  |> filter(fn: (r) => r._measurement == \"chat\")\n"
                + "  |> sort(columns: [\"_time\"], desc: false)";

        try (InfluxDBClient influxDBClient = influxDBConfig.getConnectionInfluxDBClient()) {
            List<FluxTable> tables = influxDBClient.getQueryApi().query(flux);

            return fluxTableToList(tables);
        } catch (Exception e) {
            throw new ChatNotFoundException(e.getMessage());
        }
    }

    public List<Chat> fluxTableToList(List<FluxTable> tables) {
        List<Chat> list = new ArrayList<>();
        for (FluxTable table : tables) {
            for (FluxRecord fluxRecord : table.getRecords()) {
                list.add(new Chat(fluxRecord));
            }
        }
        return list;
    }

  /* 예시 flux 쿼리입니다
    String flux = "from(bucket: \"" + bucket + "\")\n"
            + "  |> range(start: "+hour+"h)\n"
            + "  |> filter(fn: (r) => r._measurement == \"chat\" and r.nickname == \"nickname\")\n"
            + "  |> sort(columns: [\"_time\"], desc: false)";
  */
}
