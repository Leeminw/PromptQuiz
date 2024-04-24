package com.ssafy.apm.common.controller;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.common.dto.request.ChannelChatDto;
import com.ssafy.apm.common.dto.request.GameAnswerRequestDto;
import com.ssafy.apm.common.dto.request.GameChatDto;
import com.ssafy.apm.common.dto.request.GameReadyDto;
import com.ssafy.apm.common.dto.response.*;
import com.ssafy.apm.common.util.TimerGame;
import com.ssafy.apm.quiz.service.QuizService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST}, maxAge = 6000)
public class SocketController {

    private final SimpMessagingTemplate template;
    private final QuizService quizService;

    // 현재 게임 진행중인 리스트 (max_time 초 대기)
    private static final HashMap<Long, TimerGame> gameStartList = new HashMap<>();

    // 라운드 끝나고 대기중인 리스트 (REST_TIME 초 대기)
    private static final HashMap<Long, TimerGame> gameReadyList = new HashMap<>();

    private static final int REST_TIME = 3;

    // 현재 진행중인 게임 관리 스케줄러
    @Scheduled(fixedRate = 1000) // 1초마다 실행
    private void roundStartScheduler() {
        List<TimerGame> list = new ArrayList<>(gameStartList.values());
        for (TimerGame game : list) {
            // 시간 초 증가 시키기
            game.time++;
            if (game.time > game.maxTime) {
                // 게임 종료 (timeout)
                gameReadyList.put(game.gameId, game);
                gameStartList.remove(game.gameId);
                game.time = 0;

                // 게임 종료 메세지 전송
                sendGameEndMessage(game);
            } else if (game.time < 0) {
                // 게임 종료 (정답자가 나왔을 경우)
                gameReadyList.put(game.gameId, game);
                gameStartList.remove(game.gameId);
                game.time = 0;
            } else {
                // 각각의 시간초 보내주기
                template.convertAndSend("/sub/game?uuid=" + game.uuid
                        , new GameResponseDto("game", GameSystemResponseDto.timer(game.time)));

            }
        }
    }

    @Scheduled(fixedRate = 1000) // 1초마다 실행
    private void roundReadyScheduler() {
        // 전체 준비에 들어온 게임의 대기 시간을 증가시키기
        List<TimerGame> list = new ArrayList<>(gameReadyList.values());
        for (TimerGame game : list) {
            // 시간 초 증가
            game.time++;

            // 3초가 됐다면 이제 게임 시작하기
            if (game.time >= REST_TIME) {
                // quizId 1 증가시키고 다음 시작
                game.quizId++;
                game.time = 0;
                gameStartList.put(game.gameId, game);
                gameReadyList.remove(game.gameId);

                // 시작 메세지 전송
                sendGameStartMessage(game);
            } else {
                // 각각의 시간초 보내주기
                template.convertAndSend("/sub/game?uuid=" + game.uuid
                        , new GameResponseDto("game", GameSystemResponseDto.timer(game.time)));

            }
        }
    }

    // -------------------- 채팅 관련 브로커 --------------------
    // 채널에서 보내는 메세지
    @MessageMapping("/channel/chat/send")
    public void sendChannelMessage(@Payload ChannelChatDto chatMessage) {
        template.convertAndSend("/sub/channel?uuid=" + chatMessage
                .getUuid(), chatMessage);
    }

    // 게임방에서 입력 받은 메세지 전송
    @MessageMapping("/game/chat/send")
    public void sendGameChatMessage(@Payload GameChatDto chatMessage) {
        template.convertAndSend("/sub/game?uuid=" + chatMessage.
                getUuid(), new GameResponseDto("chat", chatMessage));
    }


    // -------------------- 게임 진행 관련 브로커  --------------------
    // test dump list
    List<PlayerDto> list = Arrays.asList(
            new PlayerDto("test1", 10, false),
            new PlayerDto("test2", 30, false),
            new PlayerDto("test3", 40, false),
            new PlayerDto("test4", 0, false)
    );

    // (게임 시작) 스케쥴러에 게임을 등록하고 준비 메세지 전송
    @MessageMapping("/game/start")
    public void gameStart(@Payload GameReadyDto ready) {
        // 현재 게임방에 등록되어 있지 않다면 등록시키기
        if (!gameReadyList.containsKey(ready.getGameId())) {
            TimerGame newGame = new TimerGame(ready.getGameId(), ready.getUuid(), 0L, 10, 0);
            gameReadyList.put(ready.getGameId(), newGame);
            sendGameReadyMessage(newGame);
        }
    }

    // (라운드 대기) 라운드 대기 메세지 전송
    public void sendGameReadyMessage(TimerGame game) {
        // ready 상태에서는 현재 어떤 라운드인지 알려줘야 한다.
        GameSystemContentDto temp = new GameSystemContentDto(game.quizId, null);

        template.convertAndSend("/sub/game?uuid=" + game.uuid,
                new GameResponseDto("game", GameSystemResponseDto.ready(temp)));
    }

    // (라운드 시작) 라운드 시작 메세지 전송
    public void sendGameStartMessage(TimerGame game) {
        // 라운드 시작은 단순 시작 알림과 퀴즈 아이디를 전송
        GameSystemContentDto temp = new GameSystemContentDto(game.quizId, null);

        template.convertAndSend("/sub/game?uuid=" + game.uuid,
                new GameResponseDto("game", GameSystemResponseDto.start(temp)));
    }

    // (라운드 종료) 누군가 정답을 맞추거나 timeout일 경우 라운드 종료 처리
    public void sendGameEndMessage(TimerGame game) {
        /*
            이 부분에서 quiz의 아이디가 전부 소진됐다면 result로 넘어가는 로직이 들어가야 합니다.
            sendGameResultMessage()
        */

        // 전체 사용자에게 라운드 종료 알림 보내기
        GameSystemContentDto temp = new GameSystemContentDto(game.quizId + 1, list);

        template.convertAndSend("/sub/game?uuid=" + game.uuid,
                new GameResponseDto("game", GameSystemResponseDto.end(temp)));
    }

    // (게임 종료) 전체 라운드 종료 이후 사용자 점수 리스트 반환
    public void sendGameResultMessage(TimerGame game) {
        /*
            여기에는 전체 점수를 통해 user의 랭킹 점수를 올리는 코드가 들어가야 합니다.
        */

        // 게임이 종료됐으면 전체 사용자의 점수 list를 반환해주기
        GameSystemContentDto temp = new GameSystemContentDto(0L, list);

        template.convertAndSend("/sub/game?uuid=" + game.uuid,
                new GameResponseDto("game", GameSystemResponseDto.result(temp)));
    }

    // -------------------- 플레이어 정답 입력 관련 컨트롤러  --------------------
    // (플레이어 입력) 각 플레이어가 입력한 정답 확인
    @PostMapping("/api/v1/game/answer")
    public ResponseEntity<?> registAnswer(@RequestBody GameAnswerRequestDto answer) {
        // 초기값 설정은 false로 설정
        GameAnswerResponseDto response = new GameAnswerResponseDto();

        switch (answer.getType()) {
            case "객관식":
                // 객관식 번호가 정답일 경우 true
                response.setResult(quizService.answerQuizCheck(answer));
                break;
            case "주관식":
                // 유사도 측정 이후 90% 이상의 유사도일 경우 정답처리
                response.setSimilarity(quizService.answerSimilarityCheck(answer));
                response.setResult(response.getSimilarity() > 0.9);
                break;
            case "순서":
                // 순서가 맞을 경우 true
                response.setResult(quizService.answerOrderCheck(answer));
                break;
        }

        // response의 결과가 true일 경우 정답
        if (response.getResult()) {
            TimerGame game = gameStartList.get(answer.getGameId());
            game.time = -game.maxTime;

            // 미리 종료 메세지를 보내 다른 사용자가 입력 못하게 하기
            sendGameEndMessage(game);
        }

        return sendResponse(response, HttpStatus.OK);
    }

    // response 객체 생성 메서드
    public ResponseEntity<?> sendResponse(Object response, HttpStatus status) {
        return ResponseEntity.status(status).body(ResponseData.success(response));
    }
}
