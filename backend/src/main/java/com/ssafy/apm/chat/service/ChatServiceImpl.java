package com.ssafy.apm.chat.service;

import com.influxdb.client.QueryApi;
import com.ssafy.apm.chat.domain.Chat;
import lombok.RequiredArgsConstructor;
import com.influxdb.client.write.Point;
import com.influxdb.client.InfluxDBClient;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBMapper;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

  private final InfluxDBClient influxDBClient;

  private final InfluxDB influxDB;

  @Override
  public void addChat(String content){
    Point row = Point.measurement("test")
            .addTag("nickname", "test")
            .addField("content", content)
            .addField("order", "ASC");

    influxDBClient.getWriteApiBlocking().writePoint(row);
  }

  public Chat getChatList(){
    try {
      // 쿼리 실행
      QueryResult queryResult = influxDB.query(new Query("SELECT * FROM test"));
      InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
      List<Chat> memoryPointList = resultMapper
              .toPOJO(queryResult, Chat.class);
    } finally {
      // InfluxDB 클라이언트 종료
      influxDB.close();
    }
    return null;
  }
}
