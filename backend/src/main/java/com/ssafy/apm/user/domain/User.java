package com.ssafy.apm.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public void updateProfile(String profileUrl){
        this.picture = profileUrl;
    }
    public void updateStatusMessage(String message){
        this.statusMessage = message;
    }


}
