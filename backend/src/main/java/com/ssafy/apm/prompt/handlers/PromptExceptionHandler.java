package com.ssafy.apm.prompt.handlers;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.prompt.exception.PromptNotFoundException;
import com.ssafy.apm.prompt.exception.PromptValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class PromptExceptionHandler {

    @ExceptionHandler(PromptNotFoundException.class)
    public ResponseEntity<?> handleCustomNotFoundException(PromptNotFoundException e) {
        log.error("PromptNotFoundException: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PromptValidationException.class)
    public ResponseEntity<?> handleCustomValidationException(PromptValidationException e) {
        log.error("PromptValidationException: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
