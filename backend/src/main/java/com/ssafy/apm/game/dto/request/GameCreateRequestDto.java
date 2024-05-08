package com.ssafy.apm.game.dto.request;

import com.ssafy.apm.game.domain.Game;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameCreateRequestDto {
    private String channelCode;
    private String password;
    private String title;
    private String style;

    private Integer mode;
    private Boolean isTeam;
    private Boolean isPrivate;
    private Integer timeLimit;

    private Integer maxRounds;
    private Integer maxPlayers;

    public Game toEntity() {
        return Game.builder()
                .channelCode(this.channelCode)
                .password(this.password)
                .title(this.title)
                .style(this.style)
                .mode(this.mode)
                .isTeam(this.isTeam)
                .isPrivate(this.isPrivate)
                .timeLimit(this.timeLimit)
                .isStarted(false)
                .curRounds(0)
                .maxRounds(this.maxRounds)
                .curPlayers(1)
                .maxPlayers(this.maxPlayers)
                .build();
    }

}
