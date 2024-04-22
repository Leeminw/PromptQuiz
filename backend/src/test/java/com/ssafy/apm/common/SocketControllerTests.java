package com.ssafy.apm.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.apm.common.controller.SocketController;
import com.ssafy.apm.common.service.SocketServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(SocketController.class)
@ExtendWith(MockitoExtension.class)
@Slf4j
public class SocketControllerTests {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SocketServiceImpl socketService;

    @Test
    void build() {
        log.debug("Success Build");
    }

    @Test
    @DisplayName("소켓 연결 테스트")
    void connectionTest() throws Exception {
        String roomId = "test";

        // socket connect get test
        mock.perform(MockMvcRequestBuilders.get("/socket/room/connect/" + roomId)
                        .content("connected")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
