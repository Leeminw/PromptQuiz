package com.ssafy.apm.quiz.handlers;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.prompt.exception.PromptValidationException;
import com.ssafy.apm.quiz.exception.QuizNotFoundException;

import com.ssafy.apm.quiz.exception.QuizValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class QuizExceptionHandler {
    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<?> handleCustomNotFoundException(QuizNotFoundException e) {
        log.error("QuizNotFoundException : " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QuizValidationException.class)
    public ResponseEntity<?> handleCustomValidationException(QuizValidationException e) {
        log.error("QuizValidationException: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
