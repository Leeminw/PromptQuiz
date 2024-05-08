package com.ssafy.apm.gameuser.dto.request;

import com.ssafy.apm.gameuser.domain.GameUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameUserCreateRequestDto {
    private String gameCode;
    private Long userId;
    private String team;
    private Integer score;
    private Boolean isHost;

    public GameUser toEntity() {
        return GameUser.builder()
                .team(this.team)
                .score(this.score)
                .isHost(this.isHost)
                .userId(this.userId)
                .gameCode(this.gameCode)
                .build();
    }

}
