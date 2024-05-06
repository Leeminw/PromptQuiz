package com.ssafy.apm.multiplechoice.handlers;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.multiplechoice.exception.MultipleChoiceNotFoundException;
import com.ssafy.apm.multiplechoice.exception.MultipleChoiceValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class MultipleChoiceExceptionHandler {

    @ExceptionHandler(MultipleChoiceNotFoundException.class)
    public ResponseEntity<?> handleCustomNotFoundException(MultipleChoiceNotFoundException e) {
        log.error("MultipleChoiceNotFoundException : " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipleChoiceValidationException.class)
    public ResponseEntity<?> handleCustomValidationException(MultipleChoiceValidationException e) {
        log.error("MultipleChoiceValidationException: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
