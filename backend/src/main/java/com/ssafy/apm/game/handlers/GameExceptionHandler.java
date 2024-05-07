package com.ssafy.apm.game.handlers;

import com.ssafy.apm.channel.exception.ChannelNotFoundException;
import com.ssafy.apm.channel.exception.ChannelValidationException;
import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.game.exception.GameNotFoundException;
import com.ssafy.apm.game.exception.GameValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GameExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<?> handleCustomNotFoundException(GameNotFoundException e) {
        log.error("GameNotFoundException : " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GameValidationException.class)
    public ResponseEntity<?> handleCustomValidationException(GameValidationException e) {
        log.error("GameValidationException: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
