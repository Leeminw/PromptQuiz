package com.ssafy.apm.multiplechoice.service;

import com.ssafy.apm.multiplechoice.dto.response.MultipleChoiceGetResponseDto;
import com.ssafy.apm.quiz.dto.response.QuizDetailResponseDto;

import java.util.List;

public interface MultipleChoiceService {


    List<QuizDetailResponseDto> getMultipleChoiceListByGameId(Long gameId);
}
