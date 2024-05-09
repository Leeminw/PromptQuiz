package com.ssafy.apm.game.service;

import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.game.exception.GameNotFoundException;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gamequiz.domain.GameQuiz;
import com.ssafy.apm.gamequiz.exception.GameQuizNotFoundException;
import com.ssafy.apm.gamequiz.repository.GameQuizRepository;
import com.ssafy.apm.gameuser.domain.GameUser;
import com.ssafy.apm.gameuser.service.GameUserService;
import com.ssafy.apm.quiz.domain.Quiz;
import com.ssafy.apm.quiz.exception.QuizNotFoundException;
import com.ssafy.apm.quiz.repository.QuizRepository;
import com.ssafy.apm.socket.dto.request.GameChatRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlankChoiceService {

    private final QuizRepository quizRepository;
    private final GameRepository gameRepository;
    private final GameUserService gameUserService;
    private final GameQuizRepository gameQuizRepository;

    public List<GameQuiz> createGameQuizList(Game gameEntity, Integer gameType, List<Quiz> quizList) {
        List<GameQuiz> response = new ArrayList<>();
        int curRound = 1;
        for (Quiz quiz : quizList) { // 각 퀴즈마다 4가지 문제가 생성되야함
            GameQuiz entity = GameQuiz.builder() // 정답
                    .gameCode(gameEntity.getCode())
                    .quizId(quiz.getId())
                    .type(gameType)
                    .round(curRound)
                    .isAnswer(true)
                    .build();
            response.add(entity);
            List<Quiz> randomQuizList = quizRepository.extractRandomQuizzesByStyle(quiz.getStyle(), 3) // 같은 스타일의 quiz 찾아
                    .orElseThrow(() -> new QuizNotFoundException("No entities exists by style!"));

            for (Quiz wrong : randomQuizList) {
                entity = GameQuiz.builder() // 오답
                        .gameCode(gameEntity.getCode())
                        .quizId(wrong.getId())
                        .type(gameType)
                        .round(curRound)
                        .isAnswer(false)
                        .build();
                response.add(entity);
            }
            curRound++;
        }
        return response;
    }

    public List<GameQuiz> createGameQuiz(Game gameEntity, Quiz quiz, int curRound) {
        List<GameQuiz> response = new ArrayList<>();

        GameQuiz entity = GameQuiz.builder() // 정답
                .gameCode(gameEntity.getCode())
                .quizId(quiz.getId())
                .type(2)
                .round(curRound)
                .isAnswer(true)
                .build();
        response.add(entity);

        List<Quiz> randomQuizList = quizRepository.extractRandomQuizzesByStyle(quiz.getStyle(), 3) // 같은 스타일의 quiz 찾아
                .orElseThrow(() -> new QuizNotFoundException("No entities exists by style!"));

        for (Quiz wrong : randomQuizList) {
            entity = GameQuiz.builder() // 오답
                    .gameCode(gameEntity.getCode())
                    .quizId(wrong.getId())
                    .type(2)
                    .round(curRound)
                    .isAnswer(false)
                    .build();
            response.add(entity);
        }
        return response;
    }

    public Boolean evaluateAnswers(GameChatRequestDto answer) {
        String gameCode = answer.getGameCode();
        Game game = gameRepository.findByCode(gameCode)
                .orElseThrow(() -> new GameNotFoundException("Game Not Found with code: " + gameCode));

        Integer curRound = game.getCurRounds();
        GameQuiz answerGameQuiz = gameQuizRepository.findAllByGameCodeAndRound(gameCode, curRound)
                .orElseThrow(() -> new GameQuizNotFoundException("GameQuiz Not Found with GameCode, Round: " + gameCode + ", Round " + curRound))
                .stream().filter(GameQuiz::getIsAnswer).findFirst()
                .orElseThrow(() -> new IllegalStateException("Answer Not Found"));

        Quiz correct = quizRepository.findById(answerGameQuiz.getQuizId())
                .orElseThrow(() -> new QuizNotFoundException(answerGameQuiz.getQuizId()));

        boolean isCorrect = answer.getContent().equals(correct.getKorSentence());// 프론트에서 날리는 content를 보고 처리해야함
        // 품사 별로 하나씩 날아오는지 문장을 만들어서 보내는지 확인 후 수정할 것
        gameUserService.updateGameUserScore(answer.getUserId(), isCorrect ? 10 : -2);
        return isCorrect;
    }
}
