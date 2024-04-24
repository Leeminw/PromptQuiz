package com.ssafy.apm.channel.dto.request;

import com.ssafy.apm.channel.domain.ChannelEntity;
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

    public ChannelEntity toEntity(){
        return ChannelEntity.builder()
                .code(UUID.randomUUID().toString())
                .name(this.name)
                .curPlayers(this.curPlayers)
                .maxPlayers(this.maxPlayers)
                .build();
    }

}
