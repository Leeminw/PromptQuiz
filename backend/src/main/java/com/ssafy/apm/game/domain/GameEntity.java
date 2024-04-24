package com.ssafy.apm.game.domain;

import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "game")
public class GameEntity {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    @Id
    private Long id;
    private Long channelId;
    private Integer type;
    private Integer style;
    private String code;
    private String title;
    private String password;
    private Boolean status;
    private Boolean isTeam;
    private Integer curRound;
    private Integer rounds;
    private Integer curPlayers;
    private Integer maxPlayers;

    public void updateId(Long id){
        this.id = id;
    }

    public void update(GameUpdateRequestDto dto) {
        this.id = dto.getId();
        this.channelId = dto.getChannelId();
        this.type = dto.getType();
        this.style = dto.getStyle();
        this.code = dto.getCode();
        this.title = dto.getTitle();
        if( dto.getPassword()!=null ) this.password = dto.getPassword();
        this.status = dto.getStatus();
        this.isTeam = dto.getIsTeam();
        this.curRound = dto.getCurRound();
        this.rounds = dto.getRounds();
        this.curPlayers = dto.getCurPlayers();
        this.maxPlayers = dto.getMaxPlayers();
    }

}
