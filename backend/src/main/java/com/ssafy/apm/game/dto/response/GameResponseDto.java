package com.ssafy.apm.game.dto.response;

import com.ssafy.apm.game.domain.Game;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameResponseDto {
    private String code;
    private String channelCode;
    private String password;
    private String title;
    private Integer mode;
    private String style;
    private Boolean isTeam;
    private Boolean isPrivate;
    private Boolean isStarted;
    private Integer timeLimit;
    private Integer curRounds;
    private Integer maxRounds;
    private Integer curPlayers;
    private Integer maxPlayers;

    public GameResponseDto(Game entity) {
        this.code = entity.getCode();
        this.channelCode = entity.getChannelCode();
        this.password = entity.getPassword();
        this.title = entity.getTitle();
        this.mode = entity.getMode();
        this.style = entity.getStyle();
        this.isTeam = entity.getIsTeam();
        this.isPrivate = entity.getIsPrivate();
        this.isStarted = entity.getIsStarted();
        this.timeLimit = entity.getTimeLimit();
        this.curRounds = entity.getCurRounds();
        this.maxRounds = entity.getMaxRounds();
        this.curPlayers = entity.getCurPlayers();
        this.maxPlayers = entity.getMaxPlayers();
    }
}
