package com.ssafy.apm.user.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginRequestDto {
    private String userName;
    private String password;
}
