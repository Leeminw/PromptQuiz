package com.ssafy.apm.prompt.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.prompt.dto.PromptRequestDto;
import com.ssafy.apm.prompt.dto.PromptResponseDto;
import com.ssafy.apm.prompt.service.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PromptController {

    private final PromptService promptService;

    @PostMapping("/api/v1/prompt")
    public ResponseEntity<ResponseData<?>> createPrompt(@RequestBody PromptRequestDto requestDto) {
        PromptResponseDto responseDto = promptService.createPrompt(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @PutMapping("/api/v1/prompt")
    public ResponseEntity<ResponseData<?>> updatePrompt(@RequestBody PromptRequestDto requestDto) {
        PromptResponseDto responseDto = promptService.updatePrompt(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @DeleteMapping("/api/v1/prompt/{id}")
    public ResponseEntity<ResponseData<?>> deletePrompt(@PathVariable Long id) {
        PromptResponseDto responseDto = promptService.deletePrompt(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/api/v1/prompt/{id}")
    public ResponseEntity<ResponseData<?>> findPromptById(@PathVariable Long id) {
        PromptResponseDto responseDto = promptService.findPromptById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @GetMapping("/api/v1/prompt")
    public ResponseEntity<ResponseData<?>> findAllPrompts() {
        List<PromptResponseDto> responseDto = promptService.findAllPrompts();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/api/v1/prompt/search")
    public ResponseEntity<ResponseData<?>> searchPrompts(
            @RequestParam(value = "style", required = false) String style,
            @RequestParam(value = "group", required = false) Integer group) {
        List<PromptResponseDto> responseDto = List.of();
        if (style != null) responseDto = promptService.filterPromptByStyle(style);
        else if (group != null) responseDto = promptService.filterPromptsByGroup(group);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

    @GetMapping("/api/v1/prompt/random")
    public ResponseEntity<ResponseData<?>> randomPrompts(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "group", required = false) Integer group) {
        if (group == null && limit == null) {
            PromptResponseDto responseDto = promptService.extractRandomPrompt();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
        }
        List<PromptResponseDto> responseDto = List.of();
        if (group == null && limit != null) responseDto = promptService.extractRandomPrompts(limit);
        else if (group != null && limit == null) responseDto = promptService.extractRandomPromptsByGroup(5, group);
        else if (group != null && limit != null) responseDto = promptService.extractRandomPromptsByGroup(limit, group);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(responseDto));
    }

}
