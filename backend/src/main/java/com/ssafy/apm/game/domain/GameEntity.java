package com.ssafy.apm.game.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "game")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "channel_id")
    private Long channelId;

    @Column(name = "type")
    private Integer type;

//    int 같이 wrpper형이 아닌걸 잡으면 null값이 들어갈 때 오류난다
    @Column(name = "style")
    private Integer style;

    @Column(name = "code")
    private String code;

    @Column(name = "title")
    private String title;

    @Column(name = "password")
    private  String password;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "is_team")
    private Boolean isTeam;

    @Column(name = "cur_round")
    private Integer curRound;

    @Column(name = "rounds")
    private Integer rounds;

    @Column(name = "cur_players")
    private Integer curPlayers;

    @Column(name = "max_players")
    private Integer maxPlayers;
// JPA에서 자동으로 매핑을 받으려면 table명_pk필드명이여서 user_id다
//    우승자 user pk값
    @Column(name = "user_id")
    private Long userId;
}
