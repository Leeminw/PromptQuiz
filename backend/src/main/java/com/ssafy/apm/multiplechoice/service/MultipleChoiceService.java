package com.ssafy.apm.multiplechoice.service;

import com.ssafy.apm.quiz.dto.response.QuizResponseDto;

import java.util.List;

public interface MultipleChoiceService {


    List<QuizResponseDto> getMultipleChoiceListByGameId(Long gameId);
}
