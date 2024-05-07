package com.ssafy.apm.gamequiz.handlers;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.game.exception.GameNotFoundException;
import com.ssafy.apm.game.exception.GameValidationException;
import com.ssafy.apm.gamequiz.exception.GameQuizNotFoundException;
import com.ssafy.apm.gamequiz.exception.GameQuizValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GameQuizExceptionHandler {

    @ExceptionHandler(GameQuizNotFoundException.class)
    public ResponseEntity<?> handleCustomNotFoundException(GameQuizNotFoundException e) {
        log.error("GameQuizNotFoundException : " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GameQuizValidationException.class)
    public ResponseEntity<?> handleCustomValidationException(GameQuizValidationException e) {
        log.error("GameQuizValidationException: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
