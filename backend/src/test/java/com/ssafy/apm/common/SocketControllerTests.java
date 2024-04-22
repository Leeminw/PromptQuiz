package com.ssafy.apm.common;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.apm.common.controller.SocketController;
import com.ssafy.apm.common.service.SocketServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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

}
