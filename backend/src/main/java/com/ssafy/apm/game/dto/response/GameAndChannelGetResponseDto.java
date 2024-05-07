package com.ssafy.apm.game.dto.response;

import com.ssafy.apm.channel.domain.ChannelEntity;
import com.ssafy.apm.game.domain.GameEntity;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameAndChannelGetResponseDto {
    private String id;
    private Long channelId;
    private String channelCode;
    private String channelName;
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

    public GameAndChannelGetResponseDto(GameEntity game){
        this.id = game.getId().toString();
        this.channelId = game.getChannelId();
        this.type = game.getType();
        this.style = game.getStyle();
        this.code = game.getCode();
        this.title = game.getTitle();
        this.password = game.getPassword();
        this.status = game.getStatus();
        this.isTeam = game.getIsTeam();
        this.curRound = game.getCurRound();
        this.rounds = game.getRounds();
        this.curPlayers = game.getCurPlayers();
        this.maxPlayers = game.getMaxPlayers();
    }

    public void updateChannelInfo(ChannelEntity channel){
        this.channelCode = channel.getCode();
        this.channelName = channel.getName();
    }
}
