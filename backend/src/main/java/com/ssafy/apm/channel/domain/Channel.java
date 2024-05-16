package com.ssafy.apm.channel.domain;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "channel")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private Integer curPlayers;
    private Integer maxPlayers;

    public boolean checkChannelAccess() {
        return curPlayers < maxPlayers;
    }

    public void decreaseCurPlayers(Integer count) {
        this.curPlayers = count;
    }

    public void increaseCurPlayers(Integer count) {
        this.curPlayers = count;
    }


}
