package com.ssafy.apm.gameuser.handlers;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.gameuser.exception.GameUserNotFoundException;
import com.ssafy.apm.gameuser.exception.GameUserValidationException;
import com.ssafy.apm.quiz.exception.QuizNotFoundException;
import com.ssafy.apm.quiz.exception.QuizValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GameUserExceptionHandler {

    @ExceptionHandler(GameUserNotFoundException.class)
    public ResponseEntity<?> handleCustomNotFoundException(GameUserNotFoundException e) {
        log.error("GameUserNotFoundException : " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GameUserValidationException.class)
    public ResponseEntity<?> handleCustomValidationException(GameUserValidationException e) {
        log.error("GameUserValidationException: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
