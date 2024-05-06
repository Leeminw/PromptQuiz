package com.ssafy.apm.multiplechoice.service;

import com.ssafy.apm.game.domain.GameEntity;
import com.ssafy.apm.game.exception.GameNotFoundException;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gamequiz.domain.GameQuizEntity;
import com.ssafy.apm.gamequiz.exception.GameQuizNotFoundException;
import com.ssafy.apm.gamequiz.repository.GameQuizRepository;
import com.ssafy.apm.multiplechoice.domain.MultipleChoiceEntity;
import com.ssafy.apm.multiplechoice.exception.MultipleChoiceNotFoundException;
import com.ssafy.apm.multiplechoice.repository.MultipleChoiceRepository;
import com.ssafy.apm.quiz.domain.Quiz;
import com.ssafy.apm.quiz.dto.response.QuizResponseDto;
import com.ssafy.apm.quiz.exception.QuizNotFoundException;
import com.ssafy.apm.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MultipleChoiceServiceImpl implements MultipleChoiceService {

    private final MultipleChoiceRepository multipleChoiceRepository;
    private final GameQuizRepository gameQuizRepository;
    private final QuizRepository quizRepository;
    private final GameRepository gameRepository;


    @Override
    public List<QuizResponseDto> getMultipleChoiceListByGameId(Long gameId) {

        List<Quiz> response = new ArrayList<>();

        GameEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));

        GameQuizEntity gameQuiz = gameQuizRepository.findByGameIdAndRound(gameId, game.getCurRound())
                .orElseThrow(() -> new GameQuizNotFoundException("No entity exist by gameId, round!"));

//        여기서 gameQuiz의 타입을 확인해서 타

        List<MultipleChoiceEntity> multipleChoiceList = multipleChoiceRepository.findAllByGameQuizId(gameQuiz.getId())
                .orElseThrow(() -> new MultipleChoiceNotFoundException("No entities exists by gameQuizId!"));

        for (MultipleChoiceEntity entity : multipleChoiceList) { // 보기에 해당하는 quiz를 찾아서 리스트에 add
            Quiz temp = quizRepository.findById(entity.getQuizId())
                    .orElseThrow(() -> new QuizNotFoundException(gameQuiz.getId()));
            response.add(temp);
        }

        Collections.shuffle(response);

        return response
                .stream()
                .map(QuizResponseDto::new)
                .toList();
    }
}
