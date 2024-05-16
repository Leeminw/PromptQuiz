package com.ssafy.apm.sdw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SdwSimpleResponseDto {
    private String style;
    private String prompt;
    private String url;
    private List<Resource> resources;
}
