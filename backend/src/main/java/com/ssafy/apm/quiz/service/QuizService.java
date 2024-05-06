package com.ssafy.apm.quiz.service;

import com.ssafy.apm.quiz.dto.request.QuizRequestDto;
import com.ssafy.apm.socket.dto.request.GameChatDto;
import com.ssafy.apm.socket.dto.response.GameAnswerCheck;
import com.ssafy.apm.quiz.dto.response.QuizResponseDto;

import java.util.List;
import java.util.Set;

public interface QuizService {

    GameAnswerCheck checkAnswer(GameChatDto answer, Set<String> checkPrompt);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    QuizResponseDto createQuiz(QuizRequestDto requestDto);
    QuizResponseDto updateQuiz(QuizRequestDto requestDto);
    QuizResponseDto deleteQuiz(Long id);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    QuizResponseDto findQuizById(Long id);
    List<QuizResponseDto> findAllQuizzes();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    List<QuizResponseDto> filterQuizByStyle(String style);
    List<QuizResponseDto> filterQuizzesByGroupCode(String groupCode);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    QuizResponseDto extractRandomQuiz();
    List<QuizResponseDto> extractRandomQuizzes(Integer limit);
    List<QuizResponseDto> extractRandomQuizzesByGroupCode(String groupCode, Integer limit);

}
