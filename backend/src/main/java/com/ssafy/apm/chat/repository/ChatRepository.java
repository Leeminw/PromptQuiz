package com.ssafy.apm.chat.repository;

import com.ssafy.apm.chat.domain.Chat;
import com.influxdb.client.domain.WritePrecision;
import com.ssafy.apm.common.config.InfluxDBConfig;
import com.ssafy.apm.chat.exception.ChatNotFoundException;
import com.ssafy.apm.chat.exception.ChatValidationException;

import com.influxdb.query.FluxTable;
import com.influxdb.query.FluxRecord;
import com.influxdb.client.InfluxDBClient;

import java.util.List;
import java.util.ArrayList;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;

@Repository
@RequiredArgsConstructor
public class ChatRepository {

    private final InfluxDBConfig influxDBConfig;

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

}
