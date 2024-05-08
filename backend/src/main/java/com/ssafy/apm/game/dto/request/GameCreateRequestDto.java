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
    private Integer timeLimited;
    private Integer maxPlayers;
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
                .isStarted(false)
                .timeLimited(this.timeLimited)
                .curPlayers(1)
                .maxPlayers(this.maxPlayers)
                .curRounds(0)
                .maxRounds(this.maxRounds)
                .build();
    }
}
