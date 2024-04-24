package com.ssafy.apm.gameuser.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "game-user")
public class GameUserEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;
    private Long gameId;
    private Long userId;
    private Boolean isHost;
    private Boolean isReady;
    private Integer score;
    private String team;
    private Integer ranking;

}
