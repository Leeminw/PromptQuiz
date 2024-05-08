package com.ssafy.apm.game.controller;

import com.ssafy.apm.chat.service.ChatService;
import com.ssafy.apm.game.service.GameService;
import com.ssafy.apm.gamequiz.dto.response.GameQuizGetResponseDto;
import com.ssafy.apm.quiz.service.QuizService;
import com.ssafy.apm.socket.dto.response.*;
import com.ssafy.apm.common.util.GameRoomStatus;
import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.socket.dto.request.GameChatRequestDto;
import com.ssafy.apm.socket.dto.request.GameReadyDto;
import com.ssafy.apm.gamequiz.service.GameQuizService;
import com.ssafy.apm.gameuser.service.GameUserService;
import com.ssafy.apm.gamemonitor.service.GameMonitorService;
import com.ssafy.apm.socket.dto.request.EnterUserMessageDto;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;

@Slf4j
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
    private final GameMonitorService gameMonitorService;
    private final SimpMessagingTemplate template;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static final int REST_TIME = 3;
    // 라운드 끝나고 대기중인 리스트 (REST_TIME 초 대기)
    private static final ConcurrentHashMap<String, GameRoomStatus> gameEndMap = new ConcurrentHashMap<>();
    // 라운드 끝나고 결과확인 리스트 (REST_TIME 초 대기)
    private static final ConcurrentHashMap<String, GameRoomStatus> gameReadyMap = new ConcurrentHashMap<>();
    // 현재 게임 진행중인 리스트 (max_time 초 대기)
    private static final ConcurrentHashMap<String, GameRoomStatus> gameOngoingMap = new ConcurrentHashMap<>();

    @Scheduled(fixedRate = 360000) // 1시간마다 실행
    private void saveCurrentGameList(){
        gameMonitorService.saveRoomList(gameEndMap, gameReadyMap, gameOngoingMap);
    }

    @Scheduled(fixedRate = 1000) // 1초마다 실행
    private void roundOngoingScheduler() {
        List<GameRoomStatus> list = new ArrayList<>(gameOngoingMap.values());
        for (GameRoomStatus game : list) {
            if (!gameOngoingMap.containsKey(game.gameCode) || game.round < 0) continue;

            if (game.time == 0) {
                // 게임 종료 (timeout)
                gameEndMap.put(game.gameCode, game);
                gameOngoingMap.remove(game.gameCode);
                game.time = REST_TIME;

                sendRoundEndMessage(game);
                setRoundToEnd(game);
            } else if (game.time < 0) {
                // 게임 종료 (정답자가 나왔을 경우)
                gameEndMap.put(game.gameCode, game);
                gameOngoingMap.remove(game.gameCode);
                game.time = REST_TIME;

            } else {
                template.convertAndSend("/ws/sub/game?uuid=" + game.gameCode,
                        new GameResponseDto("timer", new GameTimerResponseDto(game.time, game.round, "ongoing")));
                game.time--;
            }
        }

    }

    @Scheduled(fixedRate = 1000) // 1초마다 실행
    private void roundReadyScheduler() {
        List<GameRoomStatus> list = new ArrayList<>(gameReadyMap.values());
        for (GameRoomStatus game : list) {
            if (!gameReadyMap.containsKey(game.gameCode) || game.round < 0) continue;

            if (game.time <= 0) {
                game.time = game.maxTime;
                gameOngoingMap.put(game.gameCode, game);
                gameReadyMap.remove(game.gameCode);

                sendRoundStartMessage(game);
            } else {
                template.convertAndSend("/ws/sub/game?uuid=" + game.gameCode,
                        new GameResponseDto("timer", new GameTimerResponseDto(game.time, game.round, "ready")));
                game.time--;
            }
        }
    }

    @Scheduled(fixedRate = 1000)
    private void roundEndScheduler() {
        List<GameRoomStatus> list = new ArrayList<>(gameEndMap.values());
        for (GameRoomStatus game : list) {
            if (!gameEndMap.containsKey(game.gameCode) || game.round < 0) continue;

            if (game.time <= 0) {
                game.time = REST_TIME;
                gameReadyMap.put(game.gameCode, game);
                gameEndMap.remove(game.gameCode);

                sendRoundReadyMessage(game);
            } else {
                template.convertAndSend("/ws/sub/game?uuid=" + game.gameCode,
                        new GameResponseDto("timer", new GameTimerResponseDto(game.time, game.round, "end")));
                game.time--;
            }
        }

    }

    ////////////////////////////플레이어 채팅 입력 관련 컨트롤러/////////////////////////////////////////////////////////
    // 새로운 사용자 입장 메세지
    @MessageMapping("/game/enter")
    public void enterGameUser(@Payload EnterUserMessageDto user) {
        template.convertAndSend("/ws/sub/game?uuid=" + user.getUuid(),
                new GameResponseDto("enter", user));
    }

    // 퇴장 메세지
    @MessageMapping("/game/leave")
    public void leaveGameUser(@Payload EnterUserMessageDto user) {
        template.convertAndSend("/ws/sub/game?uuid=" + user.getUuid(),
                new GameResponseDto("leave", user));
    }

    // (플레이어 입력) 플레이어는 채팅 or 정답을 입력한다
    @MessageMapping("/game/chat/send")
    public void sendGameChat(@Payload GameChatRequestDto chatMessage) {
        GameRoomStatus game = gameOngoingMap.get(chatMessage.getGameCode());

        if (game != null && chatMessage.getRound().equals(game.round)) {
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

        GameChatResponseDto chat = chatService.insertGameChat(chatMessage);

        // 정답이든 아니든 일단 채팅은 전체 전파하기
        template.convertAndSend("/ws/sub/game?uuid=" + chat.
                getUuid(), new GameResponseDto("chat", chat));
    }

    // test dump list
    List<PlayerDto> list = Arrays.asList(
            new PlayerDto(0L, 10, false),
            new PlayerDto(1L, 30, false),
            new PlayerDto(2L, 40, false),
            new PlayerDto(3L, 0, false)
    );

    // (게임 시작) 스케쥴러에 게임을 등록하고 준      비 메세지 전송
    @PostMapping("/api/v1/game/start")
    public ResponseEntity<?> setGameStart(@RequestBody GameReadyDto ready) {
        log.debug("game start! ");
        // 게임방에 등록되어 있지 않다면 등록시키기
        if (!gameReadyMap.containsKey(ready.getGameCode())) {
            log.debug("do if ");
            log.debug("ready , {} , {}", ready.getGameCode(), ready.getUuid());
            GameRoomStatus newGame = new GameRoomStatus(ready.getGameCode(), ready.getUuid(), 0, 10,
                    0);

            // 방장일 경우에만 게임 보기가 생성됩니다
            if (gameQuizService.createAnswerGameQuiz(ready.getGameCode())) {

                // 게임 라운드 증가 (1라운드부터 시작)
                newGame.round = gameService.updateGameRoundCnt(ready.getGameCode(), true);

                // 게임 시작 메세지 전달
                sendRoundReadyMessage(newGame);

                // 스케쥴러 시작
                gameReadyMap.put(ready.getGameCode(), newGame);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("start game"));
    }

    // (라운드 대기) 라운드 대기 메세지 전송
    public void sendRoundReadyMessage(GameRoomStatus game) {
        GameSystemContentDto temp = new GameSystemContentDto(game.round);

        template.convertAndSend("/ws/sub/game?uuid=" + game.gameCode,
                new GameResponseDto("game", GameSystemResponseDto.ready(temp)));
    }

    // (라운드 시작) 라운드 시작 메세지 전송
    public void sendRoundStartMessage(GameRoomStatus game) {
        GameSystemContentDto temp = new GameSystemContentDto(game.round);

        template.convertAndSend("/ws/sub/game?uuid=" + game.gameCode,
                new GameResponseDto("game", GameSystemResponseDto.start(temp)));
    }

    // (라운드 종료) 라운드 종료 메세지 전송
    public void sendRoundEndMessage(GameRoomStatus game) {
        // 전체 사용자에게 라운드 종료 알림 보내기 (다음 라운드 증가)
        GameSystemContentDto temp = new GameSystemContentDto(game.round, list);

        template.convertAndSend("/ws/sub/game?uuid=" + game.gameCode,
                new GameResponseDto("game", GameSystemResponseDto.end(temp)));
    }

    // (라운드 종료) 현재 게임 라운드 종료상태로 만들기
    public void setRoundToEnd(GameRoomStatus game) {
        game.round = gameService.updateGameRoundCnt(game.gameCode, false);

        if (game.round < 0) {
            setGameResult(game);
            sendGameResultMessage(game);
            return;
        }

        GameQuizGetResponseDto quiz = gameQuizService.getGameQuizDetail(game.gameCode);
        if(quiz.getType() == 4){
            game.initSimilarity();
        }
    }

    // (게임 종료) 게임 종료 메세지 전송
    public void sendGameResultMessage(GameRoomStatus game) {
        GameSystemContentDto temp = new GameSystemContentDto(list);

        template.convertAndSend("/ws/sub/game?uuid=" + game.gameCode,
                new GameResponseDto("game", GameSystemResponseDto.result(temp)));
    }

    // (게임 종료) 전체 게임 종료 이후 사용자 접수 업데이트
    public void setGameResult(GameRoomStatus game) {
        // 게임 점수 적용
        /* FIXME: GameService 에서 결과 처리 시 스코어 업데이트도 함께하도록 수정 예정 */
        // gameUserService.updateUserScore(game.gameId);

        gameOngoingMap.remove(game.gameCode);
        gameEndMap.remove(game.gameCode);
        gameReadyMap.remove(game.gameCode);
    }
}
