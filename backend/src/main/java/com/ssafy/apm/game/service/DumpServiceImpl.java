package com.ssafy.apm.game.service;

import com.ssafy.apm.gamequiz.domain.GameQuizEntity;
import com.ssafy.apm.gamequiz.service.GameQuizService;
import com.ssafy.apm.quiz.domain.Quiz;
import com.ssafy.apm.socket.dto.request.GameChatRequestDto;

import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.apache.commons.text.similarity.CosineDistance;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DumpServiceImpl {

    private final GameQuizService gameQuizService;

    public void getQuizPrompt(GameChatRequestDto answer, Set<String> checkPrompt){

        Quiz quiz = gameQuizService.getCurRoundGameQuizByGameCode(answer.getGameCode());

        HashMap<String, Double> resultMap = new HashMap<>();
        for (String i : checkPrompt) {
            Double rate = calculateSimilarity(quiz., answer.getContent());
            resultMap.put(i, calculateSimilarity("테스트 입력입니다", answer.getContent()));
        }

        return resultMap;
    }

    public Double calculateSimilarity(String input, String answer){
        input = input.replace(" ", "");
        answer = answer.replace(" ", "");

        CosineDistance ld = new CosineDistance();
        int maxLen = Math.max(input.length(), answer.length());
        double temp = ld.apply(input, answer);
        return (maxLen - temp) / maxLen;
    }

}
