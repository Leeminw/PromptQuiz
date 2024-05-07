package com.ssafy.apm.common.handlers;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.common.exceptions.GlobalNotFoundException;
import com.ssafy.apm.common.exceptions.GlobalValidationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalNotFoundException.class)
    public ResponseEntity<?> handleGlobalNotFoundException(GlobalNotFoundException e) {
        log.error("GlobalExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GlobalValidationException.class)
    public ResponseEntity<?> handleGlobalValidationException(GlobalValidationException e) {
        log.error("GlobalExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("GlobalExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.error(), HttpStatus.BAD_REQUEST);
        // return new ResponseEntity<>(ResponseData.error(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException e) {
        log.error("GlobalExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.error(), HttpStatus.BAD_REQUEST);
        // return new ResponseEntity<>(ResponseData.error(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("GlobalExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.error(), HttpStatus.BAD_REQUEST);
        // return new ResponseEntity<>(ResponseData.error(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<?> handleExpiredJwtException(Exception e) {
//        log.error("GlobalExceptionHandler: " + e.getMessage());
//        return new ResponseEntity<>(ResponseData.error("401"), HttpStatus.BAD_REQUEST);
//        // return new ResponseEntity<>(ResponseData.error(e.getMessage()), HttpStatus.BAD_REQUEST);
//    }

}
