package com.ssafy.apm.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {

    private Long userId;
    private String userName;
    private String nickName;
    private String password;
    private String picture;
    private Integer totalScore;
    private Integer teamScore;
    private Integer soloScore;

}
