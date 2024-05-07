package com.ssafy.apm.game.service;

import com.ssafy.apm.channel.domain.ChannelEntity;
import com.ssafy.apm.channel.exception.ChannelNotFoundException;
import com.ssafy.apm.channel.repository.ChannelRepository;
import com.ssafy.apm.game.domain.GameEntity;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import com.ssafy.apm.game.dto.response.GameGetResponseDto;
import com.ssafy.apm.game.exception.GameNotFoundException;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gamequiz.domain.GameQuizEntity;
import com.ssafy.apm.gamequiz.repository.GameQuizRepository;
import com.ssafy.apm.gameuser.domain.GameUserEntity;
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
    private final ChannelRepository channelRepository;
    private final GameQuizRepository gameQuizRepository;
    private final GameUserRepository gameUserRepository;
    private final ChoiceService choiceService;
    private final BlankChoiceService blankChoiceService;
    private final BlankSubjectiveService blankSubjectiveService;

    @Override
    @Transactional
    public GameGetResponseDto createGame(GameCreateRequestDto gameCreateRequestDto) {
        User userEntity = userService.loadUser();
        GameEntity gameEntity = gameCreateRequestDto.toEntity();
        gameEntity = gameRepository.save(gameEntity);
        GameUserEntity gameUserEntity = GameUserEntity.builder()
                .gameCode(gameEntity.getCode())
                .userId(userEntity.getId())
                .isHost(true)
                .score(0)
                .team("NOTHING")
                .build();
        gameUserRepository.save(gameUserEntity);
        return new GameGetResponseDto(gameEntity);
    }

    @Override
    public List<GameGetResponseDto> getGameList(String channelCode) {
        List<GameEntity> entityList = gameRepository.findAllByChannelCode(channelCode)
                .orElseThrow(() -> new GameNotFoundException("No entities exists by channelId"));

        return entityList.stream()
                .map(GameGetResponseDto::new)
                .toList();
    }

    @Override
    public GameGetResponseDto getGameInfo(String gameCode) {
        GameEntity gameEntity = gameRepository.findByCode(gameCode)
                .orElseThrow(() -> new GameNotFoundException(gameCode));
        ChannelEntity channelEntity = channelRepository.findByCode(gameEntity.getChannelCode())
                .orElseThrow(() -> new ChannelNotFoundException(gameEntity.getChannelCode()));

        GameGetResponseDto dto = new GameGetResponseDto(gameEntity);
        return dto;
    }

    @Override
    @Transactional
    public GameGetResponseDto updateGameInfo(GameUpdateRequestDto gameUpdateRequestDto) {
        GameEntity gameEntity = gameRepository.findByCode(gameUpdateRequestDto.getCode())
                .orElseThrow(() -> new GameNotFoundException(gameUpdateRequestDto.getCode()));

        gameEntity.update(gameUpdateRequestDto);
        gameRepository.save(gameEntity);
        return new GameGetResponseDto(gameEntity);
    }

    @Override
    @Transactional
    public Integer updateGameRoundCnt(String gameCode, Boolean flag) {
        GameEntity gameEntity = gameRepository.findByCode(gameCode)
                .orElseThrow(() -> new GameNotFoundException(gameCode));
        Integer response = 0;
        if (flag) {
            response = gameEntity.updateCurRounds();
        } else {
            if (gameEntity.getCurRounds() >= gameEntity.getMaxRounds()) {
                return -1;
            }
            response = gameEntity.increaseRounds();
        }
        gameRepository.save(gameEntity);
        return response;
    }

    @Override
    @Transactional
    public String deleteGame(String gameCode) {
        GameEntity gameEntity = gameRepository.findByCode(gameCode)
                .orElseThrow(() -> new GameNotFoundException(gameCode));
        List<GameUserEntity> list = gameUserRepository.findAllByGameCode(gameCode)
                .orElseThrow(() -> new GameUserNotFoundException("No entities exists by gameCode"));

        gameUserRepository.deleteAll(list);
        gameRepository.delete(gameEntity);
        return gameCode;
    }

    @Transactional
    public Boolean createGameQuiz(String gameCode) {
        User user = userService.loadUser();
        GameUserEntity gameUser = gameUserRepository.findByUserId(user.getId())
                .orElseThrow(() -> new GameUserNotFoundException("No entities exists by userId"));
        if (!gameUser.getIsHost()) return false;

        GameEntity gameEntity = gameRepository.findByCode(gameCode)
                .orElseThrow(() -> new GameNotFoundException(gameCode));
        List<Quiz> quizList = createQuizListByStyle(gameEntity.getStyle(), gameEntity);
//        각 quiz마다 4가지 문제가 있어야함
        List<GameQuizEntity> gameQuizEntityList = createGameQuizListByMode(gameEntity, gameEntity.getMode(), quizList);

        gameQuizRepository.saveAll(gameQuizEntityList);
        return true;
    }

    private List<GameQuizEntity> createGameQuizListByMode(GameEntity gameEntity, Integer gameType, List<Quiz> quizList) {
        List<GameQuizEntity> mainGameQuizList = new ArrayList<>();
        switch (gameType) {
            case 1 -> mainGameQuizList = choiceService.createGameQuizList(gameEntity, gameType, quizList);
            case 2 -> mainGameQuizList = blankChoiceService.createGameQuizList(gameEntity, gameType, quizList);
            case 4 -> mainGameQuizList = blankSubjectiveService.createGameQuizList(gameEntity, gameType, quizList);
            case 3,5,6,7 -> mainGameQuizList = randomCreateGameQuizList(gameEntity, gameType, quizList);
        }

        return mainGameQuizList;
    }

    private List<GameQuizEntity> randomCreateGameQuizList(GameEntity gameEntity, Integer gameType, List<Quiz> quizList) {
        List<GameQuizEntity> response = new ArrayList<>();
        Random random = new Random();
        int curRound = 1;

        if(gameType == 3) {
            for (Quiz quiz : quizList) {
                int randomMode = random.nextInt(3) + 1;
                if(randomMode == 1) response.addAll(choiceService.createGameQuiz(gameEntity, quiz, curRound));
                if(randomMode == 2) response.addAll(blankChoiceService.createGameQuiz(gameEntity, quiz, curRound));
                curRound++;
            }
        } else if(gameType == 5) {
            for (Quiz quiz : quizList) {
                int randomMode = random.nextInt(3) + 1;
                if(randomMode == 1) response.addAll(choiceService.createGameQuiz(gameEntity, quiz, curRound));
                if(randomMode == 2) response.add(blankSubjectiveService.createGameQuiz(gameEntity, quiz, curRound));
                curRound++;
            }
        } else if(gameType == 6) {
            for(Quiz quiz : quizList) {
                int randomMode = random.nextInt(3) + 1;
                if(randomMode == 1) response.addAll(blankChoiceService.createGameQuiz(gameEntity, quiz, curRound));
                if(randomMode == 2) response.add(blankSubjectiveService.createGameQuiz(gameEntity, quiz, curRound));
                curRound++;
            }
        } else if(gameType == 7) {
            for(Quiz quiz : quizList) {
                int randomMode = random.nextInt(4) + 1;
                if(randomMode == 1) response.addAll(choiceService.createGameQuiz(gameEntity, quiz, curRound));
                if(randomMode == 2) response.addAll(blankChoiceService.createGameQuiz(gameEntity, quiz, curRound));
                if(randomMode == 3) response.add(blankSubjectiveService.createGameQuiz(gameEntity, quiz, curRound));
                curRound++;
            }
        }
        return response;
    }

    private List<Quiz> createQuizListByStyle(String gameStyle, GameEntity gameEntity) {
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
