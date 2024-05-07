package com.ssafy.apm.user.handlers;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.user.exceptions.UserNotFoundException;
import com.ssafy.apm.user.exceptions.UserValidationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleCustomNotFoundException(UserNotFoundException e) {
        log.error("UserNotFoundException: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<?> handleCustomValidationException(UserValidationException e) {
        log.error("UserValidationException: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
