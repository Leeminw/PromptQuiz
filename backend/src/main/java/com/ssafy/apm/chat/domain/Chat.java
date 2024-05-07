package com.ssafy.apm.chat.domain;

import com.influxdb.query.FluxRecord;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import com.ssafy.apm.channel.dto.request.ChannelChatRequestDto;
import com.ssafy.apm.socket.dto.request.GameChatRequestDto;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.time.ZoneId;
import java.time.Instant;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@Measurement(name = "chat")
public class Chat {

    @Column(timestamp = true)
    private Instant time;
    @Column(tag = true)
    private String uuid;
    @Column(tag = true)
    private String nickname;
    @Column(name = "content")
    private String content;

    public Chat(FluxRecord fluxRecord) {
        this.time = fluxRecord.getTime();
        this.uuid = (String) fluxRecord.getValueByKey("uuid");
        this.nickname = (String) fluxRecord.getValueByKey("nickname");
        this.content = (String) fluxRecord.getValueByKey("_value");
    }

    public Chat(Instant time, GameChatRequestDto request) {
        this.time = time;
        this.uuid = request.getUuid();
        this.nickname = request.getNickname();
        this.content = request.getContent();
    }

    public Chat(Instant time, ChannelChatRequestDto request) {
        this.time = time;
        this.uuid = request.getUuid();
        this.nickname = request.getNickname();
        this.content = request.getContent();
    }

    public String getLocalTime() {
        return LocalTime.ofInstant(this.time, ZoneId.of("UTC")).toString();
    }

}
