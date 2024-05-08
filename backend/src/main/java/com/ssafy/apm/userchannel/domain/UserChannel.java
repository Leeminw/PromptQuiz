package com.ssafy.apm.userchannel.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.index.Indexed;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_channel")
public class UserChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Indexed
    private Long channelId;
    @Indexed
    private Long userId;
}
