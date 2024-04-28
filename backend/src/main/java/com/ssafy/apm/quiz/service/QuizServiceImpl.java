package com.ssafy.apm.quiz.service;

import com.ssafy.apm.common.dto.request.GameChatDto;
import com.ssafy.apm.common.dto.response.GameAnswerCheck;
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
        Quiz entity = repository.findById(quizId)
            .orElseThrow(() -> new QuizNotFoundException(quizId));
        return new QuizDetailResponseDto(entity);
    }

    @Override
    public GameAnswerCheck checkAnswer(GameChatDto answer) {
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
                response.setSimilarity(blankShortAnswerCheck(answer));
                response.setResult(response.getSimilarity() > 0.9);
                break;
        }

        return response;
    }


    // (객관식 체크) 객관식 체크 메서드
    public Boolean multipleChoiceCheck(GameChatDto answer) {
        // 현재 라운드와 입력 라운드가 다르다면 false

        // 라운드로 퀴즈 아이디 조회
        Long quizId = 0L;

        // quiz 데이터 가져오기 (만약 퀴즈 존재하지 않는다면 false)
        Quiz quiz = repository.findById(quizId).orElse(null);
        if (quiz == null) {
            return false;
        }

        return false;
    }

    // (빈칸 주관식) 빈칸 주관식 유사도 체크 메서드
    /*
      todo: 이때 하나만 하는게 아니라 주어진 List에 있는 것을 모두 확인해야 한다.
       이후 맞은 것들을 모두 체크해줘야 함
       이때 남은 문제가 있다면 result = false, 모든 문제 정답이 나왔다면 true로 만들어야 한다.
    */
    public Double blankShortAnswerCheck(GameChatDto answer) {
        // 라운드로 퀴즈 아이디 조회
        Long quizId = 0L;

        // quiz 데이터 가져오기 (만약 퀴즈 존재하지 않는다면 false)
        // Quiz quiz = repository.findById(quizId).orElse(null);
        // if (quiz == null) return 0.0;

        // return calcSimilarity(quiz.getPrompt(), answer.getContent());
        return calcSimilarity("테스트 입력입니다", answer.getContent());
    }

    // (빈칸 객관식) 빈칸 객관식 체크 메서드
    public Boolean blankMultipleChoiceCheck(GameChatDto answer) {
        // 라운드로 퀴즈 아이디 조회
        Long quizId = 0L;

        // quiz 데이터 가져오기 (만약 퀴즈 존재하지 않는다면 false)
        Quiz quiz = repository.findById(quizId).orElse(null);
        if (quiz == null) {
            return false;
        }

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
