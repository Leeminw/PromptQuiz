package com.ssafy.apm.game.domain;

import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(timeToLive = 3600)
public class Game {
    @Id
    private String code;
    @Indexed
    private String channelCode;
    private String password;
    @Indexed
    private String title;
    @Indexed
    private Integer mode;
    @Indexed
    private Integer style;
    @Indexed
    private Boolean isTeam;
    @Indexed
    private Boolean isPrivate;
    @Indexed
    private Boolean isStarted;
    @Indexed
    private Integer timeLimit;
    @Indexed
    private Integer curRounds;
    @Indexed
    private Integer maxRounds;
    @Indexed
    private Integer curPlayers;
    @Indexed
    private Integer maxPlayers;

    public Game update(GameUpdateRequestDto requestDto) {
        if (requestDto.getChannelCode() != null) this.channelCode = requestDto.getChannelCode();
        if (requestDto.getPassword() != null) this.password = requestDto.getPassword();
        if (requestDto.getTitle() != null) this.title = requestDto.getTitle();

        if (requestDto.getMode() != null) this.mode = requestDto.getMode();
        if (requestDto.getStyle() != null) this.style = requestDto.getStyle();
        if (requestDto.getIsTeam() != null) this.isTeam = requestDto.getIsTeam();
        if (requestDto.getIsPrivate() != null) this.isPrivate = requestDto.getIsPrivate();
        if (requestDto.getIsStarted() != null) this.isStarted = requestDto.getIsStarted();
        if (requestDto.getTimeLimit() != null) this.timeLimit = requestDto.getTimeLimit();

        if (requestDto.getCurRounds() != null) this.curRounds = requestDto.getCurRounds();
        if (requestDto.getMaxRounds() != null) this.maxRounds = requestDto.getMaxRounds();
        if (requestDto.getCurPlayers() != null) this.curPlayers = requestDto.getCurPlayers();
        if (requestDto.getMaxPlayers() != null) this.maxPlayers = requestDto.getMaxPlayers();

        return this;
    }

    public Game updateIsStarted(Boolean isStarted) {
        this.isStarted = isStarted;
        return this;
    }

    public Game increaseCurRounds() {
        this.curRounds++;
        return this;
    }

    public Game decreaseCurRounds() {
        this.curRounds--;
        return this;
    }

    public Game increaseCurPlayers() {
        this.curPlayers++;
        return this;
    }

    public Game decreaseCurPlayers() {
        this.curPlayers--;
        return this;
    }

}
