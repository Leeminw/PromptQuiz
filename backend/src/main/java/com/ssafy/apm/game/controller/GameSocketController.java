package com.ssafy.apm.game.controller;

import com.ssafy.apm.chat.domain.Chat;
import com.ssafy.apm.chat.service.ChatService;
import com.ssafy.apm.game.service.GameService;
import com.ssafy.apm.gameuser.service.GameUserService;
import com.ssafy.apm.quiz.service.QuizService;
import com.ssafy.apm.socket.util.GameRoomStatus;
import com.ssafy.apm.socket.dto.response.PlayerDto;
import com.ssafy.apm.socket.dto.request.GameChatDto;
import com.ssafy.apm.socket.dto.request.EnterUserMessageDto;
import com.ssafy.apm.socket.dto.request.GameReadyDto;
import com.ssafy.apm.gamequiz.service.GameQuizService;
import com.ssafy.apm.socket.dto.response.GameAnswerCheck;
import com.ssafy.apm.socket.dto.response.GameResponseDto;
import com.ssafy.apm.socket.dto.response.GameSystemContentDto;
import com.ssafy.apm.socket.dto.response.GameTimerResponseDto;
import com.ssafy.apm.socket.dto.response.GameSystemResponseDto;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
    RequestMethod.POST}, maxAge = 6000)
public class GameSocketController {
    private final ChatService chatService;
    private final QuizService quizService;
    private final GameService gameService;
    private final GameQuizService gameQuizService;
    private final GameUserService gameUserService;
    private final SimpMessagingTemplate template;

    ////////////////////////////////////////////////////////////////////////////////////////////////

    // 현재 게임 진행중인 리스트 (max_time 초 대기)
    private static final HashMap<Long, GameRoomStatus> gameOngoingList = new HashMap<>();
    // 라운드 끝나고 결과확인 리스트 (REST_TIME 초 대기)
    private static final HashMap<Long, GameRoomStatus> gameReadyList = new HashMap<>();
    // 라운드 끝나고 대기중인 리스트 (REST_TIME 초 대기)
    private static final HashMap<Long, GameRoomStatus> gameEndList = new HashMap<>();
    private static final int REST_TIME = 3;

    // 현재 진행중인 게임 관리 스케줄러
    @Scheduled(fixedRate = 1000) // 1초마다 실행
    private void roundOngoingScheduler() {
        List<GameRoomStatus> list = new ArrayList<>(gameOngoingList.values());
        for (GameRoomStatus game : list) {
            // 만약 list에 없다면 지워진 아이디 이므로 넘어가기
            if (!gameOngoingList.containsKey(game.gameId) || game.round < 0) {
                continue;
            }

            // 시간 초 증가 시키기
            game.time++;

            if (game.time > game.maxTime) {
                // 게임 종료 (timeout)
                gameEndList.put(game.gameId, game);
                gameOngoingList.remove(game.gameId);
                game.time = 0;

                // 게임 종료 메세지 전송
                sendRoundEndMessage(game);

                // 게임 종료 상태로 만들기
                setRoundToEnd(game);
            } else if (game.time < 0) {
                // 게임 종료 (정답자가 나왔을 경우)
                gameEndList.put(game.gameId, game);
                gameOngoingList.remove(game.gameId);
                game.time = 0;

            } else {
                // 각각의 시간초 보내주기
                template.convertAndSend("/ws/sub/game?uuid=" + game.uuid,
                    new GameResponseDto("timer", new GameTimerResponseDto(game.time, game.round)));
            }
        }
    }

    @Scheduled(fixedRate = 1000) // 1초마다 실행
    private void roundReadyScheduler() {
        // 전체 준비에 들어온 게임의 대기 시간을 증가시키기
        List<GameRoomStatus> list = new ArrayList<>(gameReadyList.values());
        for (GameRoomStatus game : list) {
            // 만약 list에 없다면 지워진 아이디 이므로 넘어가기
            if (!gameReadyList.containsKey(game.gameId) || game.round < 0) {
                continue;
            }

            // 시간 초 증가
            game.time++;

            // 3초가 됐다면 이제 게임 시작하기
            if (game.time >= REST_TIME) {
                game.time = 0;
                gameOngoingList.put(game.gameId, game);
                gameReadyList.remove(game.gameId);

                // 시작 메세지 전송
                sendRoundStartMessage(game);
            } else {
                // 각각의 시간초 보내주기
                template.convertAndSend("/ws/sub/game?uuid=" + game.uuid,
                    new GameResponseDto("timer", new GameTimerResponseDto(game.time, game.round)));
            }
        }
    }

    @Scheduled(fixedRate = 1000) // 1초마다 실행
    private void roundEndScheduler() {
        // 전체 준비에 들어온 게임의 대기 시간을 증가시키기
        List<GameRoomStatus> list = new ArrayList<>(gameEndList.values());
        for (GameRoomStatus game : list) {
            // 만약 list에 없다면 지워진 아이디 이므로 넘어가기
            if (!gameEndList.containsKey(game.gameId) || game.round < 0) {
                continue;
            }

            // 시간 초 증가
            game.time++;

            // 3초가 됐다면 이제 준비로 보내기 시작하기
            if (game.time >= REST_TIME) {
                game.time = 0;
                gameReadyList.put(game.gameId, game);
                gameEndList.remove(game.gameId);

                // 준비 메세지 전송
                sendRoundReadyMessage(game);
            } else {
                // 각각의 시간초 보내주기
                template.convertAndSend("/ws/sub/game?uuid=" + game.uuid,
                    new GameResponseDto("timer", new GameTimerResponseDto(game.time, game.round)));
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // -------------------- 플레이어 채팅 입력 관련 컨트롤러  --------------------

    // 새로운 사용자 입장 메세지
    @MessageMapping("/game/enter")
    public void enterGameUser(@Payload EnterUserMessageDto user) {
        // 새로운 플레이어 입장
        template.convertAndSend("/ws/sub/game?uuid=" + user.getUuid(),
            new GameResponseDto("enter", user));
    }

    // 퇴장 메세지
    @MessageMapping("/game/leave")
    public void leaveGameUser(@Payload EnterUserMessageDto user) {
        // 플레이어 퇴장
        template.convertAndSend("/ws/sub/game?uuid=" + user.getUuid(),
            new GameResponseDto("leave", user));
    }

    // (플레이어 입력) 플레이어는 채팅 or 정답을 입력한다
    @MessageMapping("/game/chat/send")
    public void sendGameChat(@Payload GameChatDto chatMessage) {
        GameRoomStatus game = gameOngoingList.get(chatMessage.getGameId());

        // 입력이 들어왔는데 만약 현재 라운드와 전혀 다른 입력이 들어왔을 때는 채팅만 전파하기
        if (game != null && chatMessage.getRound().equals(game.round)) {
            // 입력 메세지를 먼저 확인한 이후 정답일 경우에는 result에 true가 된다.
            // 빈칸 주관식의 경우 유사도가 함께 포함된다.
            GameAnswerCheck check = quizService.checkAnswer(chatMessage,
                game.playerSimilarityMap.keySet());

            // (오답) 타입마다 처리
            switch (check.getType()) {
                // 객관식 문제 정답 처리
                case 1, 2:
                    if (check.getResult()) {
                        // (정답) 정답으로 라운드 종료 처리
                        game.time = -game.maxTime;
                        sendRoundEndMessage(game);
                        setRoundToEnd(game);
                    } else {
                        // 오답 여부 해당 사용자에게 알려주기
                        template.convertAndSend("/ws/sub/game?uuid=" + chatMessage.
                            getUuid(), new GameResponseDto("wrongSignal", chatMessage.getUserId()));
                    }
                    break;
                case 4:
                    // 게임 유사도 목록 업데이트 이후 모든 사용자에게 뿌려주기
                    game.addSimilarityToMap(chatMessage.getContent(), check.getSimilarity());

                    // 유사도 목록이 모두 비어있다면 모든 정답이 나왔으므로 종료처리 해야 한다.
                    if (game.similarityGameEnd()) {
                        game.time = -game.maxTime;
                        sendRoundEndMessage(game);
                        setRoundToEnd(game);
                    } else {
                        template.convertAndSend("/ws/sub/game?uuid=" + chatMessage.
                            getUuid(), new GameResponseDto("similarity", game.playerSimilarityMap));
                    }
                    break;
            }
        }

        Chat chat = chatService.insertGameChat(chatMessage);
        chatMessage.setCreatedDate(chat.getLocalTime());

        // 정답이든 아니든 일단 채팅은 전체 전파하기
        template.convertAndSend("/ws/sub/game?uuid=" + chatMessage.
            getUuid(), new GameResponseDto("chat", chatMessage));
    }

    // test dump list
    List<PlayerDto> list = Arrays.asList(
        new PlayerDto("test1", 10, false),
        new PlayerDto("test2", 30, false),
        new PlayerDto("test3", 40, false),
        new PlayerDto("test4", 0, false)
    );

    // (게임 시작) 스케쥴러에 게임을 등록하고 준비 메세지 전송
    @MessageMapping("/game/start")
    public void setGameStart(@Payload GameReadyDto ready) {
        // 게임방에 등록되어 있지 않다면 등록시키기
        if (!gameReadyList.containsKey(ready.getGameId())) {
            GameRoomStatus newGame = new GameRoomStatus(ready.getGameId(), ready.getUuid(), 0, 10,
                0);

            // 방장일 경우에만 게임 보기가 생성됩니다
            if (gameQuizService.createAnswerGameQuiz(ready.getGameId())) {

                // 게임 라운드 증가 (1라운드부터 시작)
                newGame.round = gameService.updateGameRoundCnt(ready.getGameId(), true);

                // 게임 타입이 빈칸 주관식일 경우에는 다음과 같이 유사도 목록 추가하기
                if (gameQuizService.getGameQuizDetail(newGame.gameId).getType() == 4) {
                    // 처음 주어지는 품사에 대한 값 넣어주기
                    HashMap<String, String> map = new HashMap<>();
                    map.put("명사", "안녕!");
                    map.put("동사", "반가워");
                    map.put("형용사", null);
                    map.put("부사", null);

                    // 받아온 품사 목록으로 초기화 시키기
                    newGame.initSimilarity(map);
                }

                // 게임 시작 메세지 전달
                sendRoundReadyMessage(newGame);

                // 스케쥴러 시작
                gameReadyList.put(ready.getGameId(), newGame);
            }
        }
    }

    // (라운드 대기) 라운드 대기 메세지 전송
    public void sendRoundReadyMessage(GameRoomStatus game) {
        // ready 상태에서는 현재 어떤 라운드인지 알려줘야 한다.
        GameSystemContentDto temp = new GameSystemContentDto(game.round);

        template.convertAndSend("/ws/sub/game?uuid=" + game.uuid,
            new GameResponseDto("game", GameSystemResponseDto.ready(temp)));
    }

    // (라운드 시작) 라운드 시작 메세지 전송
    public void sendRoundStartMessage(GameRoomStatus game) {
        GameSystemContentDto temp = new GameSystemContentDto(game.round);

        template.convertAndSend("/ws/sub/game?uuid=" + game.uuid,
            new GameResponseDto("game", GameSystemResponseDto.start(temp)));
    }

    // (라운드 종료) 라운드 종료 메세지 전송
    public void sendRoundEndMessage(GameRoomStatus game) {
        // 전체 사용자에게 라운드 종료 알림 보내기 (다음 라운드 증가)
        GameSystemContentDto temp = new GameSystemContentDto(game.round, list);

        template.convertAndSend("/ws/sub/game?uuid=" + game.uuid,
            new GameResponseDto("game", GameSystemResponseDto.end(temp)));
    }

    // (라운드 종료) 현재 게임 라운드 종료상태로 만들기
    public void setRoundToEnd(GameRoomStatus game) {
        // 해당 게임 라운드 증가
        game.round = gameService.updateGameRoundCnt(game.gameId, false);

        // 만약 라운드가 음수라면 전체 게임 종료
        if (game.round < 0) {
            setGameResult(game);
            sendGameResultMessage(game);
            return;
        }

        // 전체 사용자에게 라운드 종료 알림 보내기 (다음 라운드 증가)
        GameSystemContentDto temp = new GameSystemContentDto(game.round, list);

        // 게임 타입이 빈칸 주관식일 경우에는 다음과 같이 유사도 목록 추가하기
        if (gameQuizService.getGameQuizDetail(game.gameId).getType() == 4) {
            // 처음 주어지는 품사에 대한 값 넣어주기
            HashMap<String, String> map = new HashMap<>();
            map.put("명사", "안녕!");
            map.put("동사", "반가워");
            map.put("형용사", null);
            map.put("부사", null);

            // 받아온 품사 목록으로 초기화 시키기
            game.initSimilarity(map);
        }
    }

    // (게임 종료) 게임 종료 메세지 전송
    public void sendGameResultMessage(GameRoomStatus game) {
        // 게임이 종료됐으면 전체 사용자의 점수 list를 반환해주기
        GameSystemContentDto temp = new GameSystemContentDto(list);

        template.convertAndSend("/ws/sub/game?uuid=" + game.uuid,
            new GameResponseDto("game", GameSystemResponseDto.result(temp)));
    }

    // (게임 종료) 전체 게임 종료 이후 사용자 접수 업데이트
    public void setGameResult(GameRoomStatus game) {
        // 게임 점수 적용
        gameUserService.updateUserScore(game.gameId);

        // 일단 게임 스케쥴러 종료
        gameOngoingList.remove(game.gameId);

        // 혹시 모르니 EndList와 ReadyList에서도 삭제
        gameEndList.remove(game.gameId);
        gameReadyList.remove(game.gameId);
    }
}
