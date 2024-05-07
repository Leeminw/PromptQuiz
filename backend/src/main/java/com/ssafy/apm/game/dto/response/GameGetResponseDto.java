package com.ssafy.apm.game.dto.response;

import com.ssafy.apm.game.domain.GameEntity;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameGetResponseDto {
    private String code;
    private String channelCode;
    private String title;
    private String style;
    private Integer mode;
    private String password;
    private Boolean isTeam;
    private Boolean isPrivate;
    private Boolean isStarted;
    private Integer timeLimited;
    private Integer curPlayers;
    private Integer maxPlayers;
    private Integer curRounds;
    private Integer maxRounds;

    public GameGetResponseDto(GameEntity game) {
        this.code = game.getCode();
        this.channelCode = game.getChannelCode();
        this.title = game.getTitle();
        this.style = game.getStyle();
        this.mode = game.getMode();
        this.password = game.getPassword();
        this.isTeam = game.getIsTeam();
        this.isPrivate = game.getIsPrivate();
        this.isStarted = game.getIsStarted();
        this.timeLimited = game.getTimeLimited();
        this.curPlayers = game.getCurPlayers();
        this.maxPlayers = game.getMaxPlayers();
        this.curRounds = game.getCurRounds();
        this.maxRounds = game.getMaxRounds();
    }
}
