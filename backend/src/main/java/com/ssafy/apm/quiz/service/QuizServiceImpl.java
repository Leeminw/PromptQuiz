package com.ssafy.apm.quiz.service;

import com.ssafy.apm.quiz.dto.request.QuizRequestDto;
import com.ssafy.apm.quiz.exception.QuizNotFoundException;
import com.ssafy.apm.socket.dto.request.GameChatDto;
import com.ssafy.apm.socket.dto.response.GameAnswerCheck;
import com.ssafy.apm.quiz.domain.Quiz;
import com.ssafy.apm.quiz.repository.QuizRepository;
import com.ssafy.apm.quiz.dto.response.QuizResponseDto;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    @Override
    public GameAnswerCheck checkAnswer(GameChatDto answer, Set<String> checkPrompt) {
        // 초기값 설정은 false로 설정
        GameAnswerCheck response = new GameAnswerCheck();

        // 퀴즈 타입 찾기
        int type = 4;

        switch (type) {
            case 1:
                // 객관식 번호가 정답일 경우 true
                response.setType(1);
                response.setResult(multipleChoiceCheck(answer));
                break;
            case 2:
                // 순서가 맞을 경우 true
                response.setType(2);
                response.setResult(blankMultipleChoiceCheck(answer));
                break;
            case 4:
                // 유사도 측정 이후 90% 이상의 유사도일 경우 정답처리
                // todo: 주어진 리스트로 모두 확인해야 함
                response.setType(4);
                response.setSimilarity(blankShortAnswerCheck(answer, checkPrompt));
                break;
        }

        return response;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public QuizResponseDto createQuiz(QuizRequestDto requestDto) {
        Quiz quiz = quizRepository.save(requestDto.toEntity());
        return new QuizResponseDto(quiz);
    }

    @Override
    public QuizResponseDto updateQuiz(QuizRequestDto requestDto) {
        Quiz quiz = quizRepository.findById(requestDto.getId()).orElseThrow(
                () -> new QuizNotFoundException(requestDto.getId()));
        return new QuizResponseDto(quiz.update(requestDto));
    }

    @Override
    public QuizResponseDto deleteQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(
                () -> new QuizNotFoundException(id));
        quizRepository.delete(quiz);
        return new QuizResponseDto(quiz);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public QuizResponseDto findQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(
                () -> new QuizNotFoundException(id));
        return new QuizResponseDto(quiz);
    }

    @Override
    public List<QuizResponseDto> findAllQuizzes() {
        List<Quiz> quizs = quizRepository.findAll();
        return quizs.stream().map(QuizResponseDto::new).toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<QuizResponseDto> filterQuizByStyle(String style) {
        List<Quiz> quizs = quizRepository.findAllByStyle(style).orElseThrow(
                () -> new QuizNotFoundException(style));
        return quizs.stream().map(QuizResponseDto::new).toList();
    }

    @Override
    public List<QuizResponseDto> filterQuizzesByGroupCode(String groupCode) {
        List<Quiz> quizs = quizRepository.findAllByGroupCode(groupCode).orElseThrow(
                () -> new QuizNotFoundException(groupCode));
        return quizs.stream().map(QuizResponseDto::new).toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public QuizResponseDto extractRandomQuiz() {
        Quiz quiz = quizRepository.extractRandomQuiz().orElseThrow(
                () -> new QuizNotFoundException("No entities exists!"));
        return new QuizResponseDto(quiz);
    }

    @Override
    public List<QuizResponseDto> extractRandomQuizzes(Integer limit) {
        List<Quiz> quizs = quizRepository.extractRandomQuizzes(limit).orElseThrow(
                () -> new QuizNotFoundException("No entities exists!"));
        return quizs.stream().map(QuizResponseDto::new).toList();
    }

    @Override
    public List<QuizResponseDto> extractRandomQuizzesByGroupCode(String groupCode, Integer limit) {
        List<Quiz> quizs = quizRepository.extractRandomQuizzesByGroupCode(groupCode, limit).orElseThrow(
                () -> new QuizNotFoundException("No entities exists!"));
        return quizs.stream().map(QuizResponseDto::new).toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // (객관식 체크) 객관식 체크 메서드
    public Boolean multipleChoiceCheck(GameChatDto answer) {
        // 현재 라운드와 입력 라운드가 다르다면 false

        // 라운드로 퀴즈 아이디 조회
        Long quizId = 0L;

        // quiz 데이터 가져오기 (만약 퀴즈 존재하지 않는다면 false)
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz == null) {
            return false;
        }

        return false;
    }

    // (빈칸 주관식) 빈칸 주관식 유사도 체크 메서드
    public HashMap<String, Double> blankShortAnswerCheck(GameChatDto answer, Set<String> checkPrompt) {
        // 라운드로 퀴즈 아이디 조회
        Long quizId = 0L;

        // 현재 남은 프롬프트 품사 데이터를 확인하며 유사도 결과 return하기
        HashMap<String, Double> resultMap = new HashMap<>();
        for(String i : checkPrompt){
            // 해당 품사와 유사도 측정 이후 result에 넣어주기
            // 테스트 입력 부분에는 해당 품사(i)에 대한 정보가 들어가야 한다.
            resultMap.put(i, calcSimilarity("테스트 입력입니다",answer.getContent()));
        }
        
        return resultMap;
    }

    // (빈칸 객관식) 빈칸 객관식 체크 메서드
    public Boolean blankMultipleChoiceCheck(GameChatDto answer) {
        // 라운드로 퀴즈 아이디 조회
        Long quizId = 0L;

        // quiz 데이터 가져오기 (만약 퀴즈 존재하지 않는다면 false)
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz == null) {
            return false;
        }

        return true;
    }

    // 유사도 측정 메서드
    public Double calcSimilarity(String input, String answer) {
        // 공백 제거하기
        input = input.replace(" ", "");
        answer = answer.replace(" ", "");

        LevenshteinDistance ld = new LevenshteinDistance();
        int maxLen = Math.max(input.length(), answer.length());
        double temp = ld.apply(input, answer);

        return (maxLen - temp) / maxLen;
    }
}
