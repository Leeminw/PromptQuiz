package com.ssafy.apm.game.dto.request;

import com.ssafy.apm.game.domain.GameEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Data
@Builder
@ToString
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
    private Boolean isStarted;
    private Integer timeLimited;
    private Integer curPlayers;
    private Integer maxPlayers;
    private Integer curRounds;
    private Integer maxRounds;

    public GameEntity toEntity() {
        return GameEntity.builder()
//                UUID를 생성자에서 미리 만들어줘
                .code(UUID.randomUUID().toString())
                .channelCode(this.channelCode)
                .style(this.style)
                .title(this.title)
                .mode(this.mode)
                .password(this.password)
                .isTeam(this.isTeam)
                .isPrivate(this.isPrivate)
                .isStarted(this.isStarted)
                .timeLimited(this.timeLimited)
                .curPlayers(this.curPlayers)
                .maxPlayers(this.maxPlayers)
                .curRounds(this.curRounds)
                .maxRounds(this.maxRounds)
                .build();
    }
}
