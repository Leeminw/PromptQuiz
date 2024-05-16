package com.ssafy.apm.dottegi.dto;

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
public class DottegiResponseDto {
    String lastUpdatedUrl;
    List<Map<String, Integer>> lastUpdatedStyles;
    List<Map<String, Integer>> lastUpdatedSubjects;
    List<Map<String, Integer>> lastUpdatedObjects;
    List<Map<String, Integer>> lastUpdatedVerbs;
    List<Map<String, Integer>> lastUpdatedSubAdjectives;
    List<Map<String, Integer>> lastUpdatedObjAdjectives;

    List<Map<String, Integer>> curUpdatedStyles;
    List<Map<String, Integer>> curUpdatedSubjects;
    List<Map<String, Integer>> curUpdatedObjects;
    List<Map<String, Integer>> curUpdatedVerbs;
    List<Map<String, Integer>> curUpdatedSubAdjectives;
    List<Map<String, Integer>> curUpdatedObjAdjectives;

}
