package com.ssafy.apm.user.domain;

import com.ssafy.apm.user.dto.UserScoreUpdateRequestDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
    private String userName;
    private String nickName;
    private String password;
    private String picture;
    private String statusMessage;
    private Integer totalScore;
    private Integer teamScore;
    private Integer soloScore;
    @CreationTimestamp
    private LocalDateTime created_date;
    @UpdateTimestamp
    private LocalDateTime updated_date;

    public void encodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updateProfile(String profileUrl) {
        this.picture = profileUrl;
    }

    public void updateStatusMessage(String message) {
        this.statusMessage = message;
    }

    public void updateScore(UserScoreUpdateRequestDto requestDto) {
        this.totalScore += requestDto.getTotalScore();
        this.soloScore += requestDto.getSoloScore();
        this.teamScore += requestDto.getTeamScore();
    }

}
