package com.ssafy.apm.game.service;

import com.ssafy.apm.socket.dto.request.GameChatRequestDto;
import com.ssafy.apm.socket.dto.response.GameAnswerCheck;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameAnswerService {

    private static final int MUITIPLECHOICE = 1, BLANKCHOICE = 2, BLANKSUBJECTIVE = 4;

    private BlankSubjectiveService blankSubjectiveService;
    private BlankChoiceService blankChoiceService;
    private ChoiceService choiceService;

    public GameAnswerCheck checkAnswer(GameChatRequestDto answer, Set<String> checkPrompt) {
        // 초기값 설정은 false로 설정
        GameAnswerCheck response = new GameAnswerCheck();

        // 퀴즈 타입 찾기
        int type = 4;

        switch (type) {
            case MUITIPLECHOICE:
                // todo: 객관식 번호가 정답일 경우 true
                response.setType(MUITIPLECHOICE);
                //response.setResult();
                break;
            case BLANKCHOICE:
                // todo: 순서가 맞을 경우 true
                response.setType(BLANKCHOICE);
                //response.setResult();
                break;
            case BLANKSUBJECTIVE:
                response.setType(BLANKSUBJECTIVE);
                response.setSimilarity(blankSubjectiveService.evaluateAnswers(answer, checkPrompt));
                break;
        }
        return response;
    }
}
