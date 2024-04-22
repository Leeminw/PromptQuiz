package com.ssafy.apm.game.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameCreateRequestDto {
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
}
