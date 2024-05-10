package com.ssafy.apm.userchannel.handlers;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.quiz.exception.QuizNotFoundException;
import com.ssafy.apm.quiz.exception.QuizValidationException;
import com.ssafy.apm.userchannel.exception.UserChannelFullException;
import com.ssafy.apm.userchannel.exception.UserChannelNotFoundException;
import com.ssafy.apm.userchannel.exception.UserChannelValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class UserChannelExceptionHandler {

    @ExceptionHandler(UserChannelNotFoundException.class)
    public ResponseEntity<?> handleCustomNotFoundException(UserChannelNotFoundException e) {
        log.error("UserChannelNotFoundException : " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserChannelValidationException.class)
    public ResponseEntity<?> handleCustomValidationException(UserChannelValidationException e) {
        log.error("UserChannelValidationException: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserChannelFullException.class)
    public ResponseEntity<?> handleCustomFullException(UserChannelFullException e) {
        log.error("UserChannelFullException: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
