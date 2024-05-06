package com.ssafy.apm.multiplechoice.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.gameuser.dto.response.GameUserGetResponseDto;
import com.ssafy.apm.multiplechoice.dto.response.MultipleChoiceGetResponseDto;
import com.ssafy.apm.multiplechoice.service.MultipleChoiceService;
import com.ssafy.apm.quiz.dto.response.QuizDetailResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/multiple-choice")
@RequiredArgsConstructor
@Slf4j
public class MultipleChoiceController {

    private final MultipleChoiceService multipleChoiceService;

    @GetMapping("/multipleChoiceList/{gameId}")
    public ResponseEntity<ResponseData<?>> getMultipleChoiceListByGameId(@PathVariable(name = "gameId") Long gameId) {
        List<QuizDetailResponseDto> response = multipleChoiceService.getMultipleChoiceListByGameId(gameId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(response));
    }
}
