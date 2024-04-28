package com.ssafy.apm.prompt.service;

import com.ssafy.apm.prompt.domain.Prompt;
import com.ssafy.apm.prompt.dto.PromptRequestDto;
import com.ssafy.apm.prompt.dto.PromptResponseDto;
import com.ssafy.apm.prompt.exception.PromptNotFoundException;
import com.ssafy.apm.prompt.repository.PromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromptServiceImpl implements PromptService {

    private final PromptRepository promptRepository;

    @Override
    public PromptResponseDto createPrompt(PromptRequestDto requestDto) {
        Prompt prompt = promptRepository.save(requestDto.toEntity());
        return new PromptResponseDto(prompt);
    }

    @Override
    public PromptResponseDto updatePrompt(PromptRequestDto requestDto) {
        Prompt prompt = promptRepository.findById(requestDto.getId()).orElseThrow(
                () -> new PromptNotFoundException(requestDto.getId()));
        return new PromptResponseDto(prompt.update(requestDto));
    }

    @Override
    public PromptResponseDto deletePrompt(Long id) {
        Prompt prompt = promptRepository.findById(id).orElseThrow(
                () -> new PromptNotFoundException(id));
        promptRepository.delete(prompt);
        return new PromptResponseDto(prompt);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public PromptResponseDto findPromptById(Long id) {
        Prompt prompt = promptRepository.findById(id).orElseThrow(
                () -> new PromptNotFoundException(id));
        return new PromptResponseDto(prompt);
    }

    @Override
    public List<PromptResponseDto> findAllPrompts() {
        List<Prompt> prompts = promptRepository.findAll();
        return prompts.stream().map(PromptResponseDto::new).toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<PromptResponseDto> filterPromptByStyle(String style) {
        List<Prompt> prompts = promptRepository.findAllByStyle(style).orElseThrow(
                () -> new PromptNotFoundException(style));
        return prompts.stream().map(PromptResponseDto::new).toList();
    }

    @Override
    public List<PromptResponseDto> filterPromptsByGroupCode(String groupCode) {
        List<Prompt> prompts = promptRepository.findAllByGroupCode(groupCode).orElseThrow(
                () -> new PromptNotFoundException(groupCode));
        return prompts.stream().map(PromptResponseDto::new).toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public PromptResponseDto extractRandomPrompt() {
        Prompt prompt = promptRepository.extractRandomPrompt().orElseThrow(
                () -> new PromptNotFoundException("No entities exists!"));
        return new PromptResponseDto(prompt);
    }

    @Override
    public List<PromptResponseDto> extractRandomPrompts(Integer limit) {
        List<Prompt> prompts = promptRepository.extractRandomPrompts(limit).orElseThrow(
                () -> new PromptNotFoundException("No entities exists!"));
        return prompts.stream().map(PromptResponseDto::new).toList();
    }

    @Override
    public List<PromptResponseDto> extractRandomPromptsByGroupCode(String groupCode, Integer limit) {
        List<Prompt> prompts = promptRepository.extractRandomPromptsByGroupCode(groupCode, limit).orElseThrow(
                () -> new PromptNotFoundException("No entities exists!"));
        return prompts.stream().map(PromptResponseDto::new).toList();
    }

}
