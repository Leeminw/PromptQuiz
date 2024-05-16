package com.ssafy.apm.sdw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SdwSimpleRequestDto {
    private String style;
    private String prompt;
}
