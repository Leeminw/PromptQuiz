package com.ssafy.apm.gameuser.dto.response;

import com.ssafy.apm.gameuser.domain.GameUser;
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
    private String gameUserCode;
    private String gameCode;
    private Boolean isHost;
    private Integer score;
    private String team;

    public GameUserDetailResponseDto(User user, GameUser gameUser) {
        this.userId = gameUser.getUserId();
        this.gameUserCode = gameUser.getCode();
        this.gameCode = gameUser.getGameCode();
        this.isHost = gameUser.getIsHost();
        this.score = gameUser.getScore();
        this.team = gameUser.getTeam();

        this.userName = user.getUserName();
        this.nickName = user.getNickName();
        this.picture = user.getPicture();
        this.statusMessage = user.getStatusMessage();
        this.totalScore = user.getTotalScore();
        this.teamScore = user.getTeamScore();
        this.soloScore = user.getSoloScore();
        this.created_date = user.getCreated_date();
        this.updated_date = user.getUpdated_date();
    }

}
