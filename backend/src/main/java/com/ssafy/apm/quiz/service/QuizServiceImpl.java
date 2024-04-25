package com.ssafy.apm.quiz.service;

import com.ssafy.apm.common.dto.request.GameAnswerRequestDto;
import com.ssafy.apm.common.dto.response.GameAnswerResponseDto;
import com.ssafy.apm.common.util.TimerGame;
import com.ssafy.apm.quiz.domain.Quiz;
import com.ssafy.apm.quiz.repository.QuizRepository;
import com.ssafy.apm.quiz.exception.QuizNotFoundException;
import com.ssafy.apm.quiz.dto.response.QuizDetailResponseDto;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.text.similarity.LevenshteinDistance;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository repository;

    // 퀴즈 상세 정보 조회
    @Override
    public QuizDetailResponseDto getQuizInfo(Long quizId) {
        Quiz entity = repository.findById(quizId).orElseThrow(() -> new QuizNotFoundException(quizId));
        return new QuizDetailResponseDto(entity);
    }

    @Override
    public GameAnswerResponseDto checkAnswer(GameAnswerRequestDto answer){
        // 초기값 설정은 false로 설정
        GameAnswerResponseDto response = new GameAnswerResponseDto();

        // 현재 퀴즈 라운드와 다른 입력이 들어왔을때 예외 처리 필요
        // 딜레이로 인해 잘못된 라운드에 사용자가 들어와 있을 경우
        // if( != answer.getQuizId()) return response();

        // 퀴즈 타입 찾기
        String type = "객관식";

        switch (type) {
            case "객관식":
                // 객관식 번호가 정답일 경우 true
                response.setResult(answerQuizCheck(answer));
                break;
            case "주관식":
                // 유사도 측정 이후 90% 이상의 유사도일 경우 정답처리
                response.setSimilarity(answerSimilarityCheck(answer));
                response.setResult(response.getSimilarity() > 0.9);
                break;
            case "순서":
                // 순서가 맞을 경우 true
                response.setResult(answerOrderCheck(answer));
                break;
        }

        return response;
    }


    // (객관식 체크) 정답 번호 체크
    public Boolean answerQuizCheck(GameAnswerRequestDto answer) {
        // 현재 라운드와 입력 라운드가 다르다면 false

        // 라운드로 퀴즈 아이디 조회
        Long quizId = 0L;

        // quiz 데이터 가져오기 (만약 퀴즈 존재하지 않는다면 false)
        Quiz quiz = repository.findById(quizId).orElse(null);
        if (quiz == null) return false;

        return false;
    }

    // (주관식 체크) 정답과 유사도 측정 이후 유사도 반환
    public Double answerSimilarityCheck(GameAnswerRequestDto answer) {
        // 라운드로 퀴즈 아이디 조회
        Long quizId = 0L;

        // quiz 데이터 가져오기 (만약 퀴즈 존재하지 않는다면 false)
        Quiz quiz = repository.findById(quizId).orElse(null);
        if (quiz == null) return 0.0;

        return calcSimilarity(quiz.getPrompt(), answer.getAnswer());
    }

    // (순서 체크) 순서 맞추기 확인 코드
    public Boolean answerOrderCheck(GameAnswerRequestDto answer) {
        // 라운드로 퀴즈 아이디 조회
        Long quizId = 0L;

        // quiz 데이터 가져오기 (만약 퀴즈 존재하지 않는다면 false)
        Quiz quiz = repository.findById(quizId).orElse(null);
        if (quiz == null) return false;

        return true;
    }

    // 유사도 측정 메서드
    public double calcSimilarity(String input, String answer) {
        // 공백 제거하기
        input = input.replace(" ", "");
        answer = answer.replace(" ", "");

        LevenshteinDistance ld = new LevenshteinDistance();
        int maxLen = Math.max(input.length(), answer.length());
        double temp = ld.apply(input, answer);

        return (maxLen - temp) / maxLen;
    }
}
