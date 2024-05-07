package com.ssafy.apm.game.service;

import com.ssafy.apm.channel.domain.ChannelEntity;
import com.ssafy.apm.channel.exception.ChannelNotFoundException;
import com.ssafy.apm.channel.repository.ChannelRepository;
import com.ssafy.apm.game.domain.GameEntity;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import com.ssafy.apm.game.dto.response.GameAndChannelGetResponseDto;
import com.ssafy.apm.game.dto.response.GameGetResponseDto;
import com.ssafy.apm.game.exception.GameNotFoundException;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gamequiz.domain.GameQuizEntity;
import com.ssafy.apm.gameuser.domain.GameUserEntity;
import com.ssafy.apm.gameuser.exception.GameUserNotFoundException;
import com.ssafy.apm.gameuser.repository.GameUserRepository;
import com.ssafy.apm.multiplechoice.domain.MultipleChoiceEntity;
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
                .gameId(gameEntity.getId())
                .userId(userEntity.getId())
                .isHost(true)
                .isReady(true)
                .score(0)
                .team("NOTHING")
                .ranking(0)
                .build();
        gameUserRepository.save(gameUserEntity);
        return new GameGetResponseDto(gameEntity);
    }

    @Override
    public List<GameGetResponseDto> getGameList(Long channelId) {
        List<GameEntity> entityList = gameRepository.findAllByChannelId(channelId)
                .orElseThrow(() -> new GameNotFoundException("No entities exists by channelId"));

        return entityList.stream()
                .map(GameGetResponseDto::new)
                .toList();
    }

    @Override
    public GameAndChannelGetResponseDto getGameInfo(Long gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        ChannelEntity channelEntity = channelRepository.findById(gameEntity.getChannelId())
                .orElseThrow(() -> new ChannelNotFoundException(gameEntity.getChannelId()));

        GameAndChannelGetResponseDto dto = new GameAndChannelGetResponseDto(gameEntity);
        dto.updateChannelInfo(channelEntity);
        return dto;
    }

    @Override
    @Transactional
    public GameGetResponseDto updateGameInfo(GameUpdateRequestDto gameUpdateRequestDto) {
        GameEntity gameEntity = gameRepository.findById(gameUpdateRequestDto.getId())
                .orElseThrow(() -> new GameNotFoundException(gameUpdateRequestDto.getId()));

        gameEntity.update(gameUpdateRequestDto);
        gameRepository.save(gameEntity);
        return new GameGetResponseDto(gameEntity);
    }

    @Override
    @Transactional
    public Integer updateGameRoundCnt(Long gameId, Boolean flag) {
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        Integer response = 0;
        if (flag) {
            response = gameEntity.updateCurRound();
        } else {
            if (gameEntity.getCurRound() >= gameEntity.getRounds()) {
                return -1;
            }
            response = gameEntity.increaseRound();
        }
        gameRepository.save(gameEntity);
        return response;
    }

    @Override
    @Transactional
    public Long deleteGame(Long gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        List<GameUserEntity> list = gameUserRepository.findAllByGameId(gameId)
                .orElseThrow(() -> new GameUserNotFoundException("No entities exists by gameId"));

        gameUserRepository.deleteAll(list);
        gameRepository.delete(gameEntity);
        return gameId;
    }

    @Transactional
    public Boolean createGameQuiz(Long gameId) {
        User user = userService.loadUser();
        GameUserEntity gameUser = gameUserRepository.findByUserId(user.getId())
                .orElseThrow(() -> new GameUserNotFoundException("No entities exists by userId"));
        if (!gameUser.getIsHost()) return false;

        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        List<Quiz> quizList = createQuizListByStyle(gameEntity.getStyle(), gameEntity);
//        각 quiz마다 4가지 문제가 있어야함
        List<GameQuizEntity> gameQuizEntityList = createGameQuizListByMode(gameEntity, gameEntity.getType(), quizList);
//        게임 문제 유형
        Integer gameType = gameEntity.getType();
//        라운드 별로 문제 출제
        Integer currentRound = 1;
//        랜덤 숫자
        int randomType = 0;
        Random random = new Random();

//        문제 유형이 하나일 경우
        if (gameType == 1 || gameType == 2 || gameType == 4) {
            for (Quiz quiz : quizList) {
                GameQuizEntity entity = createGameQuizEntity(gameId, quiz.getId(), currentRound, gameType);
                currentRound += 1;
                gameQuizEntityList.add(entity);
            }
        }
//        문제 유형이 객관식, 빈칸객관식일 경우
        else if (gameType == 3) {
            for (Quiz quiz : quizList) {
                // 1 또는 2
                randomType = random.nextInt(3) + 1;
                GameQuizEntity entity = createGameQuizEntity(gameId, quiz.getId(), currentRound, randomType);
                currentRound += 1;
                gameQuizEntityList.add(entity);
            }

        }
//        문제 유형이 객관식, 빈칸주관식일 경우(1, 4)
        else if (gameType == 5) {
            int[] numbers = {1, 4}; // 선택하고 싶은 숫자들을 배열에 저장
            createGameQuizEntityList(gameId, quizList, gameQuizEntityList, currentRound, random, numbers);

        }
//          문제 유형이 빈칸객관식, 빈칸주관식일 경우(2, 4)
        else if (gameType == 6) {
            int[] numbers = {2, 4}; // 선택하고 싶은 숫자들을 배열에 저장
            createGameQuizEntityList(gameId, quizList, gameQuizEntityList, currentRound, random, numbers);
        }
//        문제 유형 랜덤일 경우
        else {
            int[] numbers = {1, 2, 4}; // 선택하고 싶은 숫자들을 배열에 저장
            createGameQuizEntityList(gameId, quizList, gameQuizEntityList, currentRound, random, numbers);

        }

        for (GameQuizEntity entity : gameQuizEntityList) {
//            객관식일 때
            if(entity.getType() == 1) {
                Quiz quiz = quizRepository.findById(entity.getQuizId())// 정답 quiz찾아
                        .orElseThrow(() -> new QuizNotFoundException(entity.getQuizId()));
                List<Quiz> quizListByGroupCode = quizRepository.extractRandomQuizzesByStyleAndGroupCode(quiz.getStyle(), quiz.getGroupCode(), 3)
                        .orElseThrow(() -> new QuizNotFoundException("No entities exists by groupCode!"));// 오답 quiz 리스트 찾아

//                정답, 오답 리스트를 받아 문제 보기 리스트를 생성하는 함수
                List<MultipleChoiceEntity> multipleChoiceEntityList = createMultipleChoiceList(entity.getId(), quiz.getId(), quizListByGroupCode);

                multipleChoiceRepository.saveAll(multipleChoiceEntityList);// 보기들 저장
            }
//            빈칸 객관식일 때
            else if(entity.getType() == 2) {

                Quiz quiz = quizRepository.findById(entity.getQuizId())
                        .orElseThrow(() -> new QuizNotFoundException(entity.getQuizId()));
                List<Quiz> randomQuizList = quizRepository.extractRandomQuizzesByStyle(quiz.getStyle(), 3) // 같은 스타일의 quiz 찾아
                        .orElseThrow(() -> new QuizNotFoundException("No entities exists by style!"));

                List<MultipleChoiceEntity> multipleChoiceEntityList = createMultipleChoiceList(entity.getId(), quiz.getId(), randomQuizList);

                multipleChoiceRepository.saveAll(multipleChoiceEntityList);
            }
//            빈칸 주관식일 때는 보기에 정답 하나만 담아
            else if(entity.getType() == 4) {
                Quiz quiz = quizRepository.findById(entity.getQuizId())
                        .orElseThrow(() -> new QuizNotFoundException(entity.getQuizId()));

                MultipleChoiceEntity answer = MultipleChoiceEntity.builder()
                        .gameQuizId(entity.getId())
                        .quizId(quiz.getId())
                        .build();

                multipleChoiceRepository.save(answer);
            }
        }

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
                if(randomMode == 2) response.addAll(blankSubjectiveService.createGameQuiz(gameEntity, quiz, curRound));
                curRound++;
            }
        } else if(gameType == 6) {
            for(Quiz quiz : quizList) {
                int randomMode = random.nextInt(3) + 1;
                if(randomMode == 1) response.addAll(blankChoiceService.createGameQuiz(gameEntity, quiz, curRound));
                if(randomMode == 2) response.addAll(blankSubjectiveService.createGameQuiz(gameEntity, quiz, curRound));
                curRound++;
            }
        } else if(gameType == 7) {
            for(Quiz quiz : quizList) {
                int randomMode = random.nextInt(4) + 1;
                if(randomMode == 1) response.addAll(choiceService.createGameQuiz(gameEntity, quiz, curRound));
                if(randomMode == 2) response.addAll(blankChoiceService.createGameQuiz(gameEntity, quiz, curRound));
                if(randomMode == 3) response.addAll(blankSubjectiveService.createGameQuiz(gameEntity, quiz, curRound));
                curRound++;
            }
        }
        return response;
    }

    private List<Quiz> createQuizListByStyle(String gameStyle, GameEntity gameEntity) {
        List<Quiz> quizList;
        if (gameStyle.equals("random")) {
            quizList = quizRepository.extractRandomQuizzes(gameEntity.getRounds())
                    .orElseThrow(() -> new QuizNotFoundException("No entities exists by random!"));
        } else {
            quizList = quizRepository.extractRandomQuizzesByStyle(gameStyle, gameEntity.getRounds())
                    .orElseThrow(() -> new QuizNotFoundException("No entities exists by style!"));
        }
        return quizList;
    }

    //    문제 보기를 만드는 함수
    private List<MultipleChoiceEntity> createMultipleChoiceList(Long gameQuizId,
                                                                Long answerQuizId,
                                                                List<Quiz> randomQuizList){
        MultipleChoiceEntity answer = MultipleChoiceEntity.builder()
                .gameQuizId(gameQuizId)
                .quizId(answerQuizId)
                .build();

        List<MultipleChoiceEntity> multipleChoiceEntityList = new ArrayList<>();

        multipleChoiceEntityList.add(answer);

        for (Quiz q : randomQuizList) {
            MultipleChoiceEntity multipleChoice = MultipleChoiceEntity.builder()
                    .gameQuizId(gameQuizId)
                    .quizId(q.getId())
                    .build();

            multipleChoiceEntityList.add(multipleChoice);
        }

        return multipleChoiceEntityList;
    }

    private void createGameQuizEntityList(Long gameId, List<Quiz> quizList, List<GameQuizEntity> gameQuizEntityList, Integer currentRound, Random random, int[] numbers) {
        int randomIndex;
        int randomType;
        for (Quiz quiz : quizList) {
            // 배열의 길이를 최대값으로 하는 랜덤 인덱스 생성
            randomIndex = random.nextInt(numbers.length);
            // 1 또는 4
            // 랜덤 인덱스를 사용하여 배열에서 하나의 숫자 선택
            randomType = numbers[randomIndex];
            GameQuizEntity entity = createGameQuizEntity(gameId, quiz.getId(), currentRound, randomType);
            currentRound += 1;
            gameQuizEntityList.add(entity);
        }
    }

    //    GameQuizEntity를 반환하는 함수
    private GameQuizEntity createGameQuizEntity(Long gameId, Long quizId, Integer round, Integer type) {
        GameQuizEntity entity = GameQuizEntity.builder()
                .gameId(gameId)
                .quizId(quizId)
                .round(round)
                .type(type)
                .build();
        return entity;
    }
}
