package com.ssafy.apm.userchannel.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserChannelUsersResponseDto {
    private Long id;
    private String nickname;
    private String picture;
    private String statusMessage;
    private Integer totalScore;
    private Integer teamScore;
    private Integer soloScore;
    private LocalDateTime createDate;
    private LocalDateTime updatedDate;
}
