package com.ssafy.apm.prompt.service;

import com.ssafy.apm.prompt.dto.PromptRequestDto;
import com.ssafy.apm.prompt.dto.PromptResponseDto;

import java.util.List;

public interface PromptService {

    PromptResponseDto createPrompt(PromptRequestDto requestDto);
    PromptResponseDto updatePrompt(PromptRequestDto requestDto);
    PromptResponseDto deletePrompt(Long id);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    PromptResponseDto findPromptById(Long id);
    List<PromptResponseDto> findAllPrompts();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    List<PromptResponseDto> filterPromptByStyle(String style);
    List<PromptResponseDto> filterPromptsByGroupCode(String groupCode);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    PromptResponseDto extractRandomPrompt();
    List<PromptResponseDto> extractRandomPrompts(Integer limit);
    List<PromptResponseDto> extractRandomPromptsByGroupCode(String groupCode, Integer limit);

}
