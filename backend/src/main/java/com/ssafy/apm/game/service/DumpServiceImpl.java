package com.ssafy.apm.game.service;

import com.ssafy.apm.gamequiz.service.GameQuizService;
import com.ssafy.apm.socket.dto.request.GameChatRequestDto;
import com.ssafy.apm.gamequiz.dto.response.GameQuizDetailResponseDto;

import lombok.RequiredArgsConstructor;

import java.util.List;
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

    public HashMap<String, Double> evaluateAnswers(GameChatRequestDto answer, Set<String> checkPrompt){
        GameQuizDetailResponseDto quiz = gameQuizService.findFirstCurrentDetailGameQuizByGameCode(answer.getGameCode());
        HashMap<String, Double> resultMap = new HashMap<>();
        for (String prompt : checkPrompt) {
            Double rate = 0.0;
            switch (prompt){
                case "kor_subject" -> rate = calculateSimilarity(quiz.getKorSubject(), answer.getContent());
                case "kor_sub_adjective" -> rate = calculateSimilarity(quiz.getKorSubAdjective(), answer.getContent());
                case "kor_object" -> rate = calculateSimilarity(quiz.getKorObject(), answer.getContent());
                case "kor_obj_adjective" -> rate = calculateSimilarity(quiz.getKorObjAdjective(), answer.getContent());
            }
            resultMap.put(prompt, rate);
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
