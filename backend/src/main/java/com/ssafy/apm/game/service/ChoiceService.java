package com.ssafy.apm.game.service;

import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.game.exception.GameNotFoundException;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gamequiz.domain.GameQuiz;
import com.ssafy.apm.gamequiz.exception.GameQuizNotFoundException;
import com.ssafy.apm.gamequiz.repository.GameQuizRepository;
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
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChoiceService {

    private final GameRepository gameRepository;
    private final QuizRepository quizRepository;
    private final GameQuizRepository gameQuizRepository;

    private final GameUserService gameUserService;

    private final Random random = new Random();

    /* 4개 생성하는 코드 */
    public List<GameQuiz> createGameQuiz(Game game, Quiz quiz, Integer curRound) {
        List<GameQuiz> response = new ArrayList<>();

        int answerNumber = random.nextInt(4) + 1;
        GameQuiz entity = GameQuiz.builder() // 정답
                .gameCode(game.getCode())
                .quizId(quiz.getId())
                .type(1)
                .round(curRound)
                .isAnswer(true)
                .number(answerNumber)
                .build();
        response.add(entity);
        List<Quiz> quizListByGroupCode = quizRepository.extractRandomQuizzesByStyleAndGroupCode(quiz.getStyle(), quiz.getGroupCode(), 3)
                .orElseThrow(() -> new QuizNotFoundException("No entities exists by groupCode!"));// 오답 quiz 리스트 찾아

        int number = 1;
        for (Quiz wrong : quizListByGroupCode) {
            if (number == answerNumber) number++;
            entity = GameQuiz.builder() // 오답
                    .gameCode(game.getCode())
                    .quizId(wrong.getId())
                    .type(1)
                    .round(curRound)
                    .isAnswer(false)
                    .number(number++)
                    .build();
            response.add(entity);
        }
        return response;
    }

    /* 40개 생성하는 코드 */
    public List<GameQuiz> createGameQuizList(Game game, Integer type, List<Quiz> quizzes) {
        List<GameQuiz> response = new ArrayList<>();
        for (int curRound = 1; curRound <= quizzes.size(); curRound++) {
            Quiz quiz = quizzes.get(curRound - 1);
            int answerNumber = random.nextInt(4) + 1;
            response.add(GameQuiz.builder()
                    .gameCode(game.getCode())
                    .quizId(quiz.getId())
                    .type(type)
                    .round(curRound)
                    .isAnswer(true)
                    .number(answerNumber)
                    .build());

            int number = 1;
            List<Quiz> wrongQuizzes = quizRepository.extractRandomQuizzesByStyleAndGroupCode(quiz.getStyle(), quiz.getGroupCode(), 3)
                    .orElseThrow(() -> new QuizNotFoundException("Quiz Not Found with GroupCode: " + quiz.getGroupCode()));
            for (Quiz wrong : wrongQuizzes) {
                if (number == answerNumber) number++;
                response.add(GameQuiz.builder()
                        .gameCode(game.getCode())
                        .quizId(wrong.getId())
                        .type(type)
                        .round(curRound)
                        .isAnswer(false)
                        .number(number++)
                        .build());
            }
        }

        return response;
    }

    /** Boolean 타입 리턴, 매개변수 GameChatRequestDto 타입
     *   정답이면 점수 추가까지 여기서 처리
     *   오답이면 점수 감점까지 여기서 처리 */
    public Boolean checkIsAnswer(GameChatRequestDto requestDto) {
        String gameCode = requestDto.getGameCode();
        Game game = gameRepository.findByCode(gameCode)
                .orElseThrow(() -> new GameNotFoundException("Game Not Found with code: " + gameCode));

        Integer curRound = game.getCurRounds();
        GameQuiz answerGameQuiz = gameQuizRepository.findAllByGameCodeAndRound(gameCode, curRound)
                .orElseThrow(() -> new GameQuizNotFoundException("GameQuiz Not Found with GameCode, Round: " + gameCode + ", Round " + curRound))
                .stream().filter(GameQuiz::getIsAnswer).findFirst()
                .orElseThrow(() -> new IllegalStateException("Answer Not Found"));

        Integer answer = answerGameQuiz.getNumber();
        boolean isCorrect = Integer.parseInt(requestDto.getContent()) == answer;
        gameUserService.updateGameUserScore(isCorrect ? 10 : -5);
        return isCorrect;
    }

}
