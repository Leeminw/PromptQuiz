package com.ssafy.apm.user.dto;

import com.ssafy.apm.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDto {

    private String userName;
    private String nickName;
    private String password;
    private String picture;

    public User toEntity() {
        return User.builder()
                .userName(this.userName)
                .nickName(this.nickName)
                .password(this.password)
                .picture(this.picture)
                .role("ROLE_USER")
                .soloScore(0)
                .teamScore(0)
                .totalScore(0)
                .build();
    }

}
