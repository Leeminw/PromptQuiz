package com.ssafy.apm.channel.dto.request;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChannelCreateRequestDto {
    private String name;
    private Integer curPlayers;
    private Integer maxPlayers;
}
