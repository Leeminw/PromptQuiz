package com.ssafy.apm.game.service;

import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import com.ssafy.apm.game.dto.response.GameResponseDto;
import com.ssafy.apm.game.exception.GameNotFoundException;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gamequiz.domain.GameQuiz;
import com.ssafy.apm.gamequiz.repository.GameQuizRepository;
import com.ssafy.apm.gameuser.domain.GameUser;
import com.ssafy.apm.gameuser.exception.GameUserNotFoundException;
import com.ssafy.apm.gameuser.repository.GameUserRepository;
import com.ssafy.apm.quiz.domain.Quiz;
import com.ssafy.apm.quiz.exception.QuizNotFoundException;
import com.ssafy.apm.quiz.repository.QuizRepository;
import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameServiceImpl implements GameService {

    private final UserService userService;
    private final GameRepository gameRepository;
    private final QuizRepository quizRepository;
    private final GameQuizRepository gameQuizRepository;
    private final GameUserRepository gameUserRepository;
    private final ChoiceService choiceService;
    private final BlankChoiceService blankChoiceService;
    private final BlankSubjectiveService blankSubjectiveService;

    @Override
    @Transactional
    public GameResponseDto createGame(GameCreateRequestDto requestDto) {
        User user = userService.loadUser();
        Game game = gameRepository.save(requestDto.toEntity());
        GameUser gameUser = GameUser.builder()
                .gameCode(game.getCode())
                .userId(user.getId())
                .isHost(true)
                .score(0)
                .team("NOTHING")
                .build();
        gameUserRepository.save(gameUser);
        return new GameResponseDto(game);
    }

    @Override
    public List<GameResponseDto> findGamesByChannelCode(String channelCode) {
        List<Game> entityList = gameRepository.findAllByChannelCode(channelCode)
                .orElseThrow(() -> new GameNotFoundException("No entities exists by channelId"));

        return entityList.stream().map(GameResponseDto::new).toList();
    }

    @Override
    public GameResponseDto findGameByGameCode(String gameCode) {
        Game gameEntity = gameRepository.findByCode(gameCode)
                .orElseThrow(() -> new GameNotFoundException(gameCode));

        GameResponseDto dto = new GameResponseDto(gameEntity);
        return dto;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    @Transactional
    public GameResponseDto updateGame(GameUpdateRequestDto gameUpdateRequestDto) {
        Game gameEntity = gameRepository.findByCode(gameUpdateRequestDto.getCode())
                .orElseThrow(() -> new GameNotFoundException(gameUpdateRequestDto.getCode()));

        gameEntity.update(gameUpdateRequestDto);
        gameRepository.save(gameEntity);
        return new GameResponseDto(gameEntity);
    }
    public Integer updateGameRoundCnt(String gameCode, Boolean flag) {
        Game game = gameRepository.findById(gameCode)
                .orElseThrow(() -> new GameNotFoundException(gameCode));
        if (flag) {
//            curRound 1로 초기화
            /* TODO: initCurRounds() 로 추가 및 수정 필요 */
//            response = game.updateCurRound();
        } else {
//        마지막 라운드라면
            if (game.getCurRounds() >= game.getMaxRounds()) {
                return -1;
            }
            game.increaseCurRounds();
        }
        gameRepository.save(game);
        return game.getCurRounds();
    }
    @Override
    @Transactional
    public Game updateGameIsStarted(String gameCode, Boolean isStarted) {
        Game gameEntity = gameRepository.findByCode(gameCode)
                .orElseThrow(() -> new GameNotFoundException(gameCode));

        gameEntity = gameEntity.updateIsStarted(isStarted);
        gameRepository.save(gameEntity);
        return gameEntity;
    }
    @Override
    @Transactional
    public GameResponseDto deleteGame(String code) {
        Game game = gameRepository.findByCode(code).orElseThrow(
                () -> new GameNotFoundException(code));
        List<GameUser> gameUsers = gameUserRepository.findAllByGameCode(code).orElseThrow(
                () -> new GameUserNotFoundException("No entities exists by gameCode: " + code));

        gameUserRepository.deleteAll(gameUsers);
        gameRepository.delete(game);
        return new GameResponseDto(game);
    }

    @Override
    @Transactional
    public Boolean createGameQuiz(String gameCode) {
        User user = userService.loadUser();
        GameUser gameUser = gameUserRepository.findByUserId(user.getId())
                .orElseThrow(() -> new GameUserNotFoundException("No entities exists by userId"));
        if (!gameUser.getIsHost()) return false;

        Game game = gameRepository.findByCode(gameCode)
                .orElseThrow(() -> new GameNotFoundException(gameCode));
        List<Quiz> quizList = createQuizListByStyle(game.getStyle(), game);
//        각 quiz마다 4가지 문제가 있어야함
        List<GameQuiz> gameQuizList = createGameQuizListByMode(game, game.getMode(), quizList);

        gameQuizRepository.saveAll(gameQuizList);
        return true;
    }

    private List<GameQuiz> createGameQuizListByMode(Game gameEntity, Integer gameType, List<Quiz> quizList) {
        List<GameQuiz> mainGameQuizList = new ArrayList<>();
        switch (gameType) {
            case 1 -> mainGameQuizList = choiceService.createGameQuizList(gameEntity, gameType, quizList);
            case 2 -> mainGameQuizList = blankChoiceService.createGameQuizList(gameEntity, gameType, quizList);
            case 4 -> mainGameQuizList = blankSubjectiveService.createGameQuizList(gameEntity, gameType, quizList);
            case 3, 5, 6, 7 -> mainGameQuizList = randomCreateGameQuizList(gameEntity, gameType, quizList);
        }

        return mainGameQuizList;
    }

    private List<GameQuiz> randomCreateGameQuizList(Game gameEntity, Integer gameType, List<Quiz> quizList) {
        List<GameQuiz> response = new ArrayList<>();
        Random random = new Random();
        int curRound = 1;

        if (gameType == 3) {
            for (Quiz quiz : quizList) {
                int randomMode = random.nextInt(3) + 1;
                if (randomMode == 1) response.addAll(choiceService.createGameQuiz(gameEntity, quiz, curRound));
                if (randomMode == 2) response.addAll(blankChoiceService.createGameQuiz(gameEntity, quiz, curRound));
                curRound++;
            }
        } else if (gameType == 5) {
            for (Quiz quiz : quizList) {
                int randomMode = random.nextInt(3) + 1;
                if (randomMode == 1) response.addAll(choiceService.createGameQuiz(gameEntity, quiz, curRound));
                if (randomMode == 2) response.add(blankSubjectiveService.createGameQuiz(gameEntity, quiz, curRound));
                curRound++;
            }
        } else if (gameType == 6) {
            for (Quiz quiz : quizList) {
                int randomMode = random.nextInt(3) + 1;
                if (randomMode == 1) response.addAll(blankChoiceService.createGameQuiz(gameEntity, quiz, curRound));
                if (randomMode == 2) response.add(blankSubjectiveService.createGameQuiz(gameEntity, quiz, curRound));
                curRound++;
            }
        } else if (gameType == 7) {
            for (Quiz quiz : quizList) {
                int randomMode = random.nextInt(4) + 1;
                if (randomMode == 1) response.addAll(choiceService.createGameQuiz(gameEntity, quiz, curRound));
                if (randomMode == 2) response.addAll(blankChoiceService.createGameQuiz(gameEntity, quiz, curRound));
                if (randomMode == 3) response.add(blankSubjectiveService.createGameQuiz(gameEntity, quiz, curRound));
                curRound++;
            }
        }
        return response;
    }

    private List<Quiz> createQuizListByStyle(String gameStyle, Game gameEntity) {
        List<Quiz> quizList;
        if (gameStyle.equals("random")) {
            quizList = quizRepository.extractRandomQuizzes(gameEntity.getMaxRounds())
                    .orElseThrow(() -> new QuizNotFoundException("No entities exists by random!"));
        } else {
            quizList = quizRepository.extractRandomQuizzesByStyle(gameStyle, gameEntity.getMaxRounds())
                    .orElseThrow(() -> new QuizNotFoundException("No entities exists by style!"));
        }
        return quizList;
    }
}
