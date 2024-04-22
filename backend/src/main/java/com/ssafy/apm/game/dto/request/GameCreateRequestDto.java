package com.ssafy.apm.game.dto.request;

import com.ssafy.apm.game.domain.GameEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameCreateRequestDto {
//    id, 방코드, 우승자 컬럼 없음
//    방코드는 UUID 만들어 Entity에 넣어서 만들거야
//    지금 만드는 놈 PK 값이 필요함
    private Long userId;
    private Long channelId;
    private Integer type;
    private Integer style;
    private String title;
    private String password;
    private Boolean status;
    private Boolean isTeam;
    private Integer curRound;
    private Integer rounds;
    private Integer curPlayers;
    private Integer maxPlayers;

    public GameEntity toEntity(){
        return GameEntity.builder()
                .channelId(this.channelId)
                .type(this.type)
                .style(this.style)
//                UUID를 생성자에서 미리 만들어줘
                .code(UUID.randomUUID().toString())
                .title(this.title)
                .password(this.password)
                .status(this.status)
                .isTeam(this.isTeam)
                .curRound(this.curRound)
                .rounds(this.curRound)
                .curPlayers(this.curPlayers)
                .maxPlayers(this.maxPlayers)
                .build();
    }
}
