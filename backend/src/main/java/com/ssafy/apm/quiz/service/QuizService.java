package com.ssafy.apm.quiz.service;

import com.ssafy.apm.prompt.dto.PromptRequestDto;
import com.ssafy.apm.prompt.dto.PromptResponseDto;
import com.ssafy.apm.quiz.dto.request.QuizRequestDto;
import com.ssafy.apm.socket.dto.request.GameChatDto;
import com.ssafy.apm.socket.dto.response.GameAnswerCheck;
import com.ssafy.apm.quiz.dto.response.QuizDetailResponseDto;

import java.util.List;
import java.util.Set;

public interface QuizService {

    GameAnswerCheck checkAnswer(GameChatDto answer, Set<String> checkPrompt);

    QuizDetailResponseDto createQuiz(QuizRequestDto requestDto);
    QuizDetailResponseDto updateQuiz(QuizRequestDto requestDto);
    QuizDetailResponseDto deleteQuiz(Long id);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    QuizDetailResponseDto findQuizById(Long id);
    List<QuizDetailResponseDto> findAllQuizs();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    List<QuizDetailResponseDto> filterQuizByStyle(String style);
    List<QuizDetailResponseDto> filterQuizsByGroupCode(String groupCode);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    QuizDetailResponseDto extractRandomQuiz();
    List<QuizDetailResponseDto> extractRandomQuizs(Integer limit);
    List<QuizDetailResponseDto> extractRandomQuizsByGroupCode(String groupCode, Integer limit);
}
