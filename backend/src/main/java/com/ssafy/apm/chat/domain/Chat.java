package com.ssafy.apm.chat.domain;

import com.influxdb.query.FluxRecord;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@Measurement(name = "chat")
public class Chat {
  @Column(timestamp = true)
  private Instant time;
  @Column(tag = true)
  private Long uuid;
  @Column(tag = true)
  private String nickname;
  @Column(name = "content")
  private String content;

  public Chat(FluxRecord fluxRecord) {
    this.time = fluxRecord.getTime();
    this.content = (String) fluxRecord.getValueByKey("_value");
    this.nickname = (String) fluxRecord.getValueByKey("nickname");
    this.uuid = Long.valueOf((String) Objects.requireNonNull(fluxRecord.getValueByKey("uuid")));
  }

}
