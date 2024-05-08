package com.ssafy.apm.game.dto.request;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameUpdateRequestDto {
    private String code;
    private String channelCode;
    private String password;
    private String title;
    private String style;
    private Integer mode;
    private Boolean isTeam;
    private Boolean isPrivate;
    private Boolean isStarted;
    private Integer timeLimit;
    private Integer curPlayers;
    private Integer maxPlayers;
    private Integer curRounds;
    private Integer maxRounds;
}
