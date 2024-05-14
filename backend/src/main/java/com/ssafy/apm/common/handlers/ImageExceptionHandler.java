package com.ssafy.apm.common.handlers;

import com.ssafy.apm.common.domain.ResponseData;
import com.ssafy.apm.common.exceptions.ImageNotFoundException;
import com.ssafy.apm.common.exceptions.ImageValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Slf4j
@ControllerAdvice
public class ImageExceptionHandler {

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<?> handleImageNotFoundException(ImageNotFoundException e) {
        log.error("ImageExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImageValidationException.class)
    public ResponseEntity<?> handleImageValidationException(ImageValidationException e) {
        log.error("ImageExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException e) {
        log.error("ImageExceptionHandler: " + e.getMessage());
        return new ResponseEntity<>(ResponseData.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
