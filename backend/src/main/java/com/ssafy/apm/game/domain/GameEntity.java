package com.ssafy.apm.game.domain;

import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
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
    private  String password;
    private Boolean status;
    private Boolean isTeam;
    private Integer curRound;
    private Integer rounds;
    private Integer curPlayers;
    private Integer maxPlayers;

}
