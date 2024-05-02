package com.ssafy.apm.chat.domain;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import lombok.Data;

import java.time.Instant;

@Data
@Measurement(name = "chat")
public class Chat {
  @Column(timestamp = true)
  private Instant time;
  @Column(tag = true)
  private String nickname;
  @Column(name="content")
  private String content;
  @Column(name="uuid")
  private Long uuid;
}
