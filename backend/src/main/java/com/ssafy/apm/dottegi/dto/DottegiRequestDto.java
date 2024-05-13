package com.ssafy.apm.dottegi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DottegiRequestDto {
    private String style;
    private String prompt;
}
