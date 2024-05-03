package com.ssafy.apm.userchannel.dto.response;

import com.ssafy.apm.userchannel.domain.UserChannelEntity;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserChannelGetResponseDto {
    private Long id;
    private Long channelId;
    private Long userId;

    public UserChannelGetResponseDto(UserChannelEntity entity){
        this.id = entity.getId();
        this.channelId = entity.getChannelId();
        this.userId = entity.getUserId();
    }
}
