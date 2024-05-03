package com.ssafy.apm.channel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
