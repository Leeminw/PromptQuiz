package com.ssafy.apm.channel.dto.response;

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
}
