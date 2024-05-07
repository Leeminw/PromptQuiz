package com.ssafy.apm.channel.handlers;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.channel.exception.ChannelNotFoundException;
import com.ssafy.apm.channel.exception.ChannelValidationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ChannelExceptionHandler {

    @ExceptionHandler(ChannelNotFoundException.class)
    public ResponseEntity<?> handleCustomNotFoundException(ChannelNotFoundException e) {
        log.error("ChannelNotFoundException : " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChannelValidationException.class)
    public ResponseEntity<?> handleCustomValidationException(ChannelValidationException e) {
        log.error("ChannelValidationException: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
