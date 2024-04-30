package com.ssafy.apm.sdw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SdwResponseDto {
    private List<String> images;
    private Map<String, Object> parameters;
    private String info;
}
