package com.ssafy.apm.channel.dto.request;

import com.ssafy.apm.channel.domain.Channel;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChannelCreateRequestDto {

    private String name;
    private Integer curPlayers;
    private Integer maxPlayers;

    public Channel toEntity() {
        return Channel.builder()
                .code(UUID.randomUUID().toString())
                .name(this.name)
                .curPlayers(this.curPlayers)
                .maxPlayers(this.maxPlayers)
                .build();
    }

}
