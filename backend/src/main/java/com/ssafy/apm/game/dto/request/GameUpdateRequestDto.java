package com.ssafy.apm.game.dto.request;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameUpdateRequestDto {
    private Long id;
    private Long channelId;
    private Integer type;
    private String style;
    private String code;
    private String title;
    private String password;
    private Boolean status;
    private Boolean isTeam;
    private Integer curRound;
    private Integer rounds;
    private Integer curPlayers;
    private Integer maxPlayers;
}
