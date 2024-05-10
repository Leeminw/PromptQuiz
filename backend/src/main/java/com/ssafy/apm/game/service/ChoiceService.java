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

    /** 문제 1개(보기 4개) 생성하는 코드 */
    public List<GameQuiz> createGameQuiz(Game game, Quiz quiz, Integer numRound) {
        return createSelections(game, quiz, numRound);
    }

    /** 문제 10개(보기 40개) 생성하는 코드 */
    public List<GameQuiz> createGameQuizList(Game game, List<Quiz> quizzes) {
        List<GameQuiz> response = new ArrayList<>();
        for (int numRound = 1; numRound <= quizzes.size(); numRound++) {
            Quiz quiz = quizzes.get(numRound - 1);
            response.addAll(createSelections(game, quiz, numRound));
        }
        return response;
    }

    /**
     * 채팅 메세지로부터 GameCode 를 통해 현재 라운드의 정답값과 Content 비교
     * content 가 정답 번호라면 GameUser 라운드 점수 증가
     * content 가 오답 번호라면 GameUser 라운드 점수 감소
     * @param requestDto gameCode, content
     * @return 정답이면 true, 오답이면 false
     */
    public Boolean checkAnswer(GameChatRequestDto requestDto) {
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
        gameUserService.updateGameUserScore(requestDto.getUserId(), isCorrect ? 10 : -5);
        return isCorrect;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private List<GameQuiz> createSelections(Game game, Quiz quiz, Integer round) {
        List<GameQuiz> selections = new ArrayList<>();
        int answerNumber = random.nextInt(4) + 1;
        selections.add(GameQuiz.builder()
                .gameCode(game.getCode())
                .quizId(quiz.getId())
                .type(1)
                .round(round)
                .isAnswer(true)
                .number(answerNumber)
                .build());

        int number = 1;
        List<Quiz> quizzes = quizRepository.extractRandomQuizzesByStyleAndGroupCode(quiz.getStyle(), quiz.getGroupCode(), 3)
                .orElseThrow(() -> new QuizNotFoundException("Quiz Not Found with GroupCode: " + quiz.getGroupCode()));
        for (Quiz wrongQuiz : quizzes) {
            if (number == answerNumber) number++;
            selections.add(GameQuiz.builder()
                    .gameCode(game.getCode())
                    .quizId(wrongQuiz.getId())
                    .type(1)
                    .round(round)
                    .isAnswer(false)
                    .number(number++)
                    .build());
        }

        return selections;
    }

}
