package com.ssafy.apm.gamemonitor.handler;

import com.ssafy.apm.gamemonitor.exception.GameMonitorValidationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GameMonitorExceptionHandler {

    @ExceptionHandler(GameMonitorValidationException.class)
    public void handleCustomValidationException(GameMonitorValidationException e) {
        log.error("GameMonitorValidationException: " + e.getMessage());
    }

}
