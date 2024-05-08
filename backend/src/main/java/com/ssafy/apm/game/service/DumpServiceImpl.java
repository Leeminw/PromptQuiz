package com.ssafy.apm.game.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.text.similarity.CosineDistance;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DumpServiceImpl {

    public void getQuizPrompt(String gameCode){

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
