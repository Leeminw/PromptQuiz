package com.ssafy.apm.gameuser.dto.response;

import com.ssafy.apm.gameuser.domain.GameUserEntity;
import com.ssafy.apm.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameUserDetailResponseDto {
    private Long userId;
    private String userName;
    private String nickName;
    private String picture;
    private String statusMessage;
    private Integer totalScore;
    private Integer teamScore;
    private Integer soloScore;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;
    private Long gameUserId;
    private Long gameId;
    private Boolean isHost;
    private Boolean isReady;
    private Integer score;
    private String team;
    private Integer ranking;

    public GameUserDetailResponseDto(GameUserEntity entity){
        this.userId = entity.getUserId();
        this.gameUserId = entity.getId();
        this.gameId = entity.getGameId();
        this.isHost = entity.getIsHost();
        this.isReady = entity.getIsReady();
        this.score = entity.getScore();
        this.team = entity.getTeam();
        this.ranking = entity.getRanking();
    }

    public void setUser(User entity){
        this.userName = entity.getUserName();
        this.nickName = entity.getNickName();
        this.picture = entity.getPicture();
        this.statusMessage = entity.getStatusMessage();
        this.totalScore = entity.getTotalScore();
        this.teamScore = entity.getTeamScore();
        this.soloScore = entity.getSoloScore();
        this.created_date = entity.getCreated_date();
        this.updated_date = entity.getUpdated_date();
    }
}
