package com.ssafy.apm.channel.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "channel")
public class ChannelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private Integer curPlayers;
    private Integer maxPlayers;

    public void decreaseCurPlayers() {
        this.curPlayers -= 1;
    }

    public void increaseCurPlayers() {
        this.curPlayers += 1;
    }
}
