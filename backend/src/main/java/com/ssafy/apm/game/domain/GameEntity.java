package com.ssafy.apm.game.domain;

import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "game", timeToLive = 3600)
public class GameEntity {

    @Id
    private String code;
    @Indexed
    private String channelCode;
    private String title;
    private String style;
    private Integer mode;
    private String password;
    private Boolean isTeam;
    private Boolean isPrivate;
    @Indexed
    private Boolean isStarted;
    private Integer timeLimited;
    @Indexed
    private Integer curPlayers;
    @Indexed
    private Integer maxPlayers;
    private Integer curRounds;
    private Integer maxRounds;


    public void update(GameUpdateRequestDto dto) {
        this.code = dto.getCode();
        this.channelCode = dto.getChannelCode();
        this.title = dto.getTitle();
        this.style = dto.getStyle();
        this.mode = dto.getMode();
        if (dto.getPassword() != null) this.password = dto.getPassword();
        this.isTeam = dto.getIsTeam();
        this.isPrivate = dto.getIsPrivate();
        this.isStarted = dto.getIsStarted();
        this.timeLimited = dto.getTimeLimited();
        this.curPlayers = dto.getCurPlayers();
        this.maxPlayers = dto.getMaxPlayers();
        this.curRounds = dto.getCurRounds();
        this.maxRounds = dto.getMaxRounds();
    }

    public void updateCode(String code) {
        this.code = code;
    }

    public void updateIsStarted(Boolean status) {
        this.status = status;
    }

    public Integer updateCurRound() {
        this.curRound = 1;
        return this.curRound;
    }

    public Integer increaseRound() {
        this.curRound += 1;
        return this.curRound;
    }

    public void increaseCurPlayers() {
        this.curPlayers += 1;
        if(this.curPlayers.equals(this.maxPlayers)){ // 방이 꽉차면
            this.status = false; // 입장 불가
        }
    }

    public void decreaseCurPlayers() {
        this.curPlayers -= 1;
    }

}
