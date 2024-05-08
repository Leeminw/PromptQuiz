package com.ssafy.apm.channel.dto.response;

import com.ssafy.apm.channel.domain.Channel;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChannelGetResponseDto {

    private Long id;
    private String code;
    private String name;
    private Integer curPlayers;
    private Integer maxPlayers;

    public ChannelGetResponseDto(Channel entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.curPlayers = entity.getCurPlayers();
        this.maxPlayers = entity.getMaxPlayers();
    }

}
