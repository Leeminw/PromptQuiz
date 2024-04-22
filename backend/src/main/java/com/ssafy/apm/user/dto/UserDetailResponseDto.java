package com.ssafy.apm.user.dto;

import com.ssafy.apm.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponseDto {
    private Long userId;
    private String userName;
    private String nickName;
    private String password;
    private String picture;
    private Integer totalScore;
    private Integer teamScore;
    private Integer soloScore;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;

    public UserDetailResponseDto(User user){
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.nickName = user.getNickName();
        this.password = user.getPassword();
        this.picture = user.getPicture();
        this.totalScore = user.getTotalScore();
        this.teamScore = user.getTeamScore();
        this.soloScore = user.getSoloScore();
        this.created_date = user.getCreated_date();
        this.updated_date = user.getUpdated_date();
    }
}
