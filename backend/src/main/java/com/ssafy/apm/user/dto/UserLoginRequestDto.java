package com.ssafy.apm.user.dto;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestDto {
    private String userName;
    private String password;
}
