package com.ssafy.apm.game.dto.request;

import com.ssafy.apm.game.domain.Game;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameCreateRequestDto {
    private String channelCode;
    private String title;
    private String style;
    private Integer mode;
    private String password;
    private Boolean isTeam;
    private Boolean isPrivate;
    private Integer timeLimit;
    private Integer maxRounds;
    private Integer maxPlayers;

    public Game toEntity() {
        return Game.builder()
                .channelCode(this.channelCode)
                .title(this.title)
                .style(this.style)
                .mode(this.mode)
                .password(this.password)
                .isTeam(this.isTeam)
                .isPrivate(this.isPrivate)
                .isStarted(false)
                .timeLimit(this.timeLimit)
                .curPlayers(1)
                .maxPlayers(this.maxPlayers)
                .curRounds(0)
                .maxRounds(this.maxRounds)
                .build();
    }

}
