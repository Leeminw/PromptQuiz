package com.ssafy.apm.chat.handler;

import com.ssafy.apm.chat.exception.ChatNotFoundException;
import com.ssafy.apm.user.exceptions.UserNotFoundException;
import com.ssafy.apm.chat.exception.ChatValidationException;
import com.ssafy.apm.user.exceptions.UserValidationException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ChatExceptionHandler {

  @ExceptionHandler(ChatNotFoundException.class)
  public void handleCustomNotFoundException(UserNotFoundException e) {
    log.error("ChatNotFoundException: " + e.getMessage());
  }

  @ExceptionHandler(ChatValidationException.class)
  public void handleCustomValidationException(UserValidationException e) {
    log.error("ChatValidationException: " + e.getMessage());
  }
}
