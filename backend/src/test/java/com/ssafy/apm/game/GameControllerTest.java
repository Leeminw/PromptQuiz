package com.ssafy.apm.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.apm.game.controller.GameController;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.response.GameGetResponseDto;
import com.ssafy.apm.game.service.GameServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GameServiceImpl gameService;

    @Test
    @DisplayName("방 정보 등록")
    void testRegist() throws Exception {
        // 방 등록 관련 테스트
        GameCreateRequestDto requestDto = new GameCreateRequestDto(1L,1L,0,0,"들어와",null,true,false,0,0,1,8);


        // JSON 변환
        String requestBody = objectMapper.writeValueAsString(requestDto);


        mock.perform(post("/api/game")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))).andDo(print());
    }

    @Test
    @DisplayName("방 정보 리스트 조회")
    void testSelectList() throws Exception {
        // given
        List<GameGetResponseDto> response = new ArrayList<>();
        response.add(GameGetResponseDto.builder().build());

        // 채널이 1인 게임방 리스트 조회
        mock.perform(get("/api/game/getGameList")
                        .param("channelId","1"))
                .andExpect(status().isOk());
//        제대로 1이 넘어왔는지 검증하는 부분이라 그냥 하드 코딩
        verify(gameService).getGameList(1L);
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.data[0].id").value(response.get(0).getId()))
//                .andExpect(jsonPath("$.data[0].channelId").value(response.get(0).getChannelId()))
//                .andExpect(jsonPath("$.data[0].type").value(response.get(0).getType()))
//                .andExpect(jsonPath("$.data[0].style").value(response.get(0).getStyle()))
//                .andExpect(jsonPath("$.data[0].code").value(response.get(0).getCode()))
//                .andExpect(jsonPath("$.data[0].title").value(response.get(0).getTitle()))
//                .andExpect(jsonPath("$.data[0].password").value(response.get(0).getPassword()))
//                .andExpect(jsonPath("$.data[0].status").value(response.get(0).getStatus()))
//                .andExpect(jsonPath("$.data[0].isTeam").value(response.get(0).getIsTeam()))
//                .andExpect(jsonPath("$.data[0].curRound").value(response.get(0).getCurRound()))
//                .andExpect(jsonPath("$.data[0].rounds").value(response.get(0).getRounds()))
//                .andExpect(jsonPath("$.data[0].curPlayers").value(response.get(0).getCurPlayers()))
//                .andExpect(jsonPath("$.data[0].maxPlayers").value(response.get(0).getMaxPlayers()))
//                .andExpect((content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))).andDo(print());
    }


}
