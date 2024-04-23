package com.ssafy.apm.common.controller;

import com.ssafy.apm.common.dto.request.ChannelChatDto;
import com.ssafy.apm.common.dto.request.GameAnswerDto;
import com.ssafy.apm.common.dto.request.GameChatDto;
import com.ssafy.apm.common.dto.response.GameResponseDto;
import com.ssafy.apm.common.dto.response.GameSystemContentDto;
import com.ssafy.apm.common.dto.response.GameSystemResponseDto;
import com.ssafy.apm.common.dto.response.PlayerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
public class SocketController {

    private final SimpMessagingTemplate template;

    // -------------------- 채팅 관련 브로커 --------------------

    // 채널에서 보내는 메세지
    @MessageMapping("/channel/chat/send")
    public void sendChannelMessage(@Payload ChannelChatDto chatMessage) {
        template.convertAndSend("/sub/channel?uuid="+chatMessage
                .getUuid(), chatMessage);
    }

    // 게임방에서 입력 받은 메세지 전송
    @MessageMapping("/game/chat/send")
    public void sendGameChatMessage(@Payload GameChatDto chatMessage) {
        template.convertAndSend("/sub/game?uuid="+chatMessage.
                getUuid(), new GameResponseDto("chat",chatMessage));
    }


    // -------------------- 게임 진행 관련 브로커  --------------------

    // test dump list
    List<PlayerDto> list = Arrays.asList(
            new PlayerDto("test1", 10, false),
            new PlayerDto("test2", 30, false),
            new PlayerDto("test3", 40, false),
            new PlayerDto("test4", 0, false)
    );

    // (플레이어 입력) 각 플레이어가 입력한 정답 확인
    @MessageMapping("/game/answer")
    public void registAnswer(@Payload GameAnswerDto answer){
        // 만약 정답이 맞을 경우 정답 처리 이후 end시키기
        if(true){
            /*
                이 부분에는 제출 답안에 따라서 업데이트 하는 부분이 들어가야 합니다.
            */
            endGameMessage(answer);
        }else{
            // 정답이 아니라면 어떻게 할 것인지
            /*
                이 부분에는 제출 답안에 따라서 업데이트 하는 부분이 들어가야 합니다.
            */
        }
    }

    // (라운드 대기) 라운드 대기 상태로 메세지 전송
    @MessageMapping("/game/ready")
    public void sendGameReadyMessage(@Payload GameAnswerDto answer) {
        // ready 상태에서는 현재 어떤 라운드인지 알려줘야 한다.
        GameSystemContentDto temp = new GameSystemContentDto(answer.getQuizId()+1,null);

        template.convertAndSend("/sub/game?uuid="+answer.getUuid(),
                new GameResponseDto("game",GameSystemResponseDto.ready(temp)));
    }

    // (라운드 시작) 다음 게임을 시작
    @MessageMapping("/game/start")
    public void sendGameStartMessage(@Payload GameAnswerDto answer) {
        // 게임을 시작할때는 입력으로 받은 answer의 quiz를 시작하면 됨
        GameSystemContentDto temp = new GameSystemContentDto(answer.getQuizId(),null);

        template.convertAndSend("/sub/game?uuid="+answer.getUuid(),
                new GameResponseDto("game",GameSystemResponseDto.start(temp)));
    }

    // (타임아웃) 시간 초과시 입력에 따라서 게임 종료 처리
    @MessageMapping("/game/timeout")
    public void sendGameEndMessage(@Payload GameAnswerDto answer) {
        // timeout은 정답이 안나온 상황 (어쨌든 종료 시키기)
        endGameMessage(answer);
    }

    // (라운드 종료) 누군가 정답을 맞추거나 timeout일 경우 라운드 종료 처리
    public void endGameMessage(GameAnswerDto answer){
        /*
            이 부분에서 quiz의 아이디가 전부 소진됐다면 result로 넘어가는 로직이 들어가야 합니다.
        */

        // 전체 사용자에게 라운드 종료 알림 보내기
        GameSystemContentDto temp = new GameSystemContentDto(answer.getQuizId()+1,list);

        template.convertAndSend("/sub/game?uuid="+answer.getUuid(),
                new GameResponseDto("game",GameSystemResponseDto.end(temp)));
    }

    // (게임 종료) 전체 라운드 종료 이후 사용자 점수 리스트 반환
    public void sendGameResultMessage(@Payload GameAnswerDto answer) {
        /*
            여기에는 전체 점수를 통해 user의 랭킹 점수를 올리는 코드가 들어가야 합니다.
        */
        
        // 게임이 종료됐으면 전체 사용자의 점수 list를 반환해주기
        GameSystemContentDto temp = new GameSystemContentDto(0L, list);

        template.convertAndSend("/sub/game?uuid="+answer.getUuid(),
                new GameResponseDto("game",GameSystemResponseDto.result(temp)));
    }
}
